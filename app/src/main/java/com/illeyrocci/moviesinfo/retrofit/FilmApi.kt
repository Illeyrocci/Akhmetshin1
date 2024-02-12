package com.illeyrocci.moviesinfo.retrofit

import com.illeyrocci.moviesinfo.Model.Film
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
const val TYPE_PARAMETER = "TOP_100_POPULAR_FILMS"

interface FilmApi {
    @Headers("type: $TYPE_PARAMETER", "x-api-key: $API_KEY")
    @GET("/api/v2.2/films/top")
    suspend fun getMovies(): FilmCollection

    @Headers("x-api-key: $API_KEY")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Film
}
