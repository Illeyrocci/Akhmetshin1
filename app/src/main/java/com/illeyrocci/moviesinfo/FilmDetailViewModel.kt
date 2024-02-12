package com.illeyrocci.moviesinfo

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.illeyrocci.moviesinfo.Model.Film
import com.illeyrocci.moviesinfo.retrofit.FilmApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.UUID

class FilmDetailViewModel(filmId: Int) : ViewModel() {
    val filmRepository = FilmRepository.get()
    var film: Film? = null

    init {
        viewModelScope.launch {
                try {
                    val id = filmId
                    film = filmRepository.getFilm(id)
                    val d = 32
                    val s = 23
                } catch (e: Exception) {
                    throw NetworkErrorException(e)
                }
        }
    }


}

class CrimeDetailViewModelFactory(
    private val filmId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmDetailViewModel(filmId) as T
    }
}