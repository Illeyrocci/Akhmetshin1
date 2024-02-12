package com.illeyrocci.moviesinfo.retrofit

import com.illeyrocci.moviesinfo.Model.Film

data class FilmCollection(
    val total: Int,
    val pagesCount: Int,
    val films: ArrayList<Film>
)