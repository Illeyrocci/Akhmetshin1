package com.illeyrocci.moviesinfo

import android.app.Application

class MoviesInfoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FilmRepository.initialize(this)
    }
}