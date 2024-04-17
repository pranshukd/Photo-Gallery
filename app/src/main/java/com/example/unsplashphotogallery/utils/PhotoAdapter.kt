package com.example.unsplashphotogallery.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashphotogallery.R
import com.example.unsplashphotogallery.databinding.ItemImageBinding
import com.example.unsplashphotogallery.datamodels.CachedImage
import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse.UnsplashImage
import com.example.unsplashphotogallery.room.CachedImageDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PhotoAdapter(
    private val cachedImageDao: CachedImageDao,
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(
) {

    private val photos = mutableListOf<UnsplashImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int = photos.size

    fun setPhotos(newPhotos: List<UnsplashImage>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyItemRangeChanged(0, photos.size)
//        notifyItemRangeChanged(photos.size - newPhotos.size, newPhotos.size)
    }

    inner class PhotoViewHolder(var binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(unsplashImage: UnsplashImage) {
            try {
                binding.img.setImageResource(R.drawable.ic_image)
                unsplashImage.id?.let { id ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val cachedImage = withContext(Dispatchers.IO) {
                            getCachedImage(unsplashImage.id)
                        }
                        if (cachedImage != null) {
                            // If cached, load bitmap from byte array
                            val bitmap = BitmapFactory.decodeFile(cachedImage.imagePath)
                            binding.img.setImageBitmap(bitmap)
                        } else {
                            unsplashImage.urls?.regular?.let { url ->
                                val imageDownloader =
                                    ImageDownloader(
                                        url,
                                        object : ImageDownloader.OnImageDownloadedListener {
                                            override fun onImageDownloaded(bitmap: Bitmap?) {
                                                if (bitmap != null) {
                                                    binding.img.setImageBitmap(bitmap)
//                                            cacheImageToDatabase(unsplashImage.id, unsplashImage.urls.regular, bitmap)
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        saveBitmapToCacheAndDb(
                                                            bitmap,
                                                            id,
                                                            url
                                                        )
                                                    }
                                                } else {
                                                    binding.img.setImageResource(R.drawable.ic_image_not_supported)
                                                }
                                            }
                                        })
                                imageDownloader.execute()
                            } ?: let {
                                binding.img.setImageResource(R.drawable.ic_image_not_supported)
                            }
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                binding.img.setImageResource(R.drawable.ic_image_not_supported)
            }
        }

        private suspend fun getCachedImage(id: String): CachedImage? {
            return withContext(Dispatchers.IO) {
                cachedImageDao.getImage(id)
            }
        }

        suspend fun saveBitmapToCacheAndDb(bitmap: Bitmap, id: String, url: String) {
            withContext(Dispatchers.IO) {
                val cacheDir = binding.root.context.cacheDir
                val imageFile = File(cacheDir, "$id.jpg")
                val outputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                val imagePath = imageFile.absolutePath
                val cachedImage = CachedImage(id, url, imagePath)
                cachedImageDao.insertImage(cachedImage)
            }
        }

    }

}