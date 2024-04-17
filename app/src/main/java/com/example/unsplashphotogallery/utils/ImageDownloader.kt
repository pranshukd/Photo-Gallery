package com.example.unsplashphotogallery.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloader(private val imageUrl: String, private val listener: OnImageDownloadedListener) :
    AsyncTask<Void, Void, Bitmap?>() {

    override fun doInBackground(vararg params: Void?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        listener.onImageDownloaded(result)
    }

    interface OnImageDownloadedListener {
        fun onImageDownloaded(bitmap: Bitmap?)
    }
}