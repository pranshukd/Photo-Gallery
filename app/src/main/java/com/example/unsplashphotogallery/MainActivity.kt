package com.example.unsplashphotogallery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unsplashphotogallery.databinding.ActivityMainBinding
import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse
import com.example.unsplashphotogallery.utils.PhotoAdapter
import com.example.unsplashphotogallery.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.photos.observe(this){ handleApiResponse(it)}
        viewModel.getRandomImages(20,500, applicationContext)
        setAdapter()
    }

    private fun setAdapter() {
        photoAdapter = PhotoAdapter()
        binding.rvPhotos.adapter = photoAdapter
    }

    private fun handleApiResponse(response: GetRandomImagesResponse?) {
        response?.let {
            photoAdapter.setPhotos(it)
        }
    }
}