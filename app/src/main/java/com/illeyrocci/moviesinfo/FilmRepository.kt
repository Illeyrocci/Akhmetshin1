package com.illeyrocci.moviesinfo

import android.content.Context
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Room
import com.illeyrocci.moviesinfo.Model.Film
import com.illeyrocci.moviesinfo.database.FilmDatabase
import com.illeyrocci.moviesinfo.retrofit.FilmApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DATABASE_NAME = "chosen-database"
private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"

class FilmRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val chosenDatabase: FilmDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,
            DATABASE_NAME
        )
        .build()


    private val movieApi = prepareFilmApi()

    suspend fun getTopFilms(): List<Film> = withContext(Dispatchers.IO) {
        val filmCollection =
            movieApi.getMovies()
        filmCollection.films.toList()
    }

    suspend fun getFilm(id: Int): Film = withContext(Dispatchers.IO) {
        movieApi.getMovieById(id)
    }

    fun getChosenFilms(): Flow<List<Film>> = chosenDatabase.filmDao().getChosenFilms()

    suspend fun addChosenFilm(film: Film) {
        coroutineScope.launch {
            chosenDatabase.filmDao().addChosenFilm(film)
        }
    }

    suspend fun deleteChosenFilm(film: Film) {
        coroutineScope.launch {
            chosenDatabase.filmDao().deleteChosenFilm(film)
        }
    }

    private fun prepareFilmApi(): FilmApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(FilmApi::class.java)
    }

    companion object {
        private var INSTANCE: FilmRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FilmRepository(context)
            }
        }

        fun get(): FilmRepository {
            return INSTANCE
                ?: throw IllegalStateException("FilmRepository must be initialized")
        }
    }
}