package com.example.unsplashphotogallery.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse
import com.example.unsplashphotogallery.network.UnsplashRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UnsplashRepo) : ViewModel() {

    private val _photos = MutableLiveData<GetRandomImagesResponse>()
    val photos: LiveData<GetRandomImagesResponse> get() = _photos

    fun getRandomImages(page: Int, perPage: Int, context: Context) {
        viewModelScope.launch {
            try {
                _photos.value = repository.getRandomImages(page, perPage)
            } catch (e: Exception) {
                handleApiError(e, context)
            }
        }
    }

    private fun handleApiError(e: Exception, context: Context) {
        val errorMessage = when (e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                parseErrorMessage(errorBody) ?: "Unknown error occurred"
            }

            is IOException -> {
                "Network error occurred. Please check your internet connection."
            }

            else -> {
                "Unknown error occurred"
            }
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun parseErrorMessage(errorBody: String?): String? {
        errorBody?.let {
            try {
                val jsonObject = JSONObject(it)
                val errorsArray = jsonObject.getJSONArray("errors")
                return errorsArray[0].toString()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return null
    }

}