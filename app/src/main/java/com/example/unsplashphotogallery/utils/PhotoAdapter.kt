package com.example.unsplashphotogallery.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashphotogallery.R
import com.example.unsplashphotogallery.databinding.ItemImageBinding
import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse.UnsplashImage
import java.util.concurrent.Executors

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

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
    }

    class PhotoViewHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(unsplashImage: UnsplashImage) {
            unsplashImage.urls?.regular?.let { url ->
                val imageDownloader = ImageDownloader(url, object : ImageDownloader.OnImageDownloadedListener {
                    override fun onImageDownloaded(bitmap: Bitmap?) {
                        if (bitmap != null) {
                            binding.img.setImageBitmap(bitmap)
                        } else {
                            binding.img.setImageResource(R.drawable.ic_image_not_supported)
                        }
                    }
                })
                imageDownloader.execute()
            }?:let {
                binding.img.setImageResource(R.drawable.ic_image_not_supported)
            }
        }

    }

}