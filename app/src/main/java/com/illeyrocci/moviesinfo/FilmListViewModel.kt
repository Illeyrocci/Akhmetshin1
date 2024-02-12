package com.illeyrocci.moviesinfo

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.illeyrocci.moviesinfo.Model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class FilmListViewModel : ViewModel() {
    private val filmRepository = FilmRepository.get()

    val _inChosenList: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val inChosenList: StateFlow<Boolean>
        get() = _inChosenList.asStateFlow()

    private val _films: MutableStateFlow<List<Film>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<Film>>
        get() = _films.asStateFlow()

    private val _chosenFilms: MutableStateFlow<List<Film>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            inChosenList.collect {
                _films.value = if (it) {
                    _chosenFilms.value
                } else {
                    var topFilms = mutableListOf<Film>()
                    try {
                        topFilms = filmRepository.getTopFilms().toMutableList()
                    } catch (e: Exception) {
                        throw NetworkErrorException(e)
                    }

                    for (i in topFilms.indices) { //тут какойто баг
                        if (contains(topFilms[i].copy(chosen = true), _chosenFilms.value))
                            topFilms[i] = topFilms[i].copy(chosen = true)
                    }
                    topFilms
                }
            }
        }
    }

    fun updateFilm(newFilm: Film) {
        _films.update { oldFilmList ->
            val newFilmList = oldFilmList.toMutableList()
            findPosition(newFilm)?.let { newFilmList[it] = newFilm }
            newFilmList.toList()
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (newFilm.chosen) {
                filmRepository.addChosenFilm(newFilm)
            } else {
                filmRepository.deleteChosenFilm(newFilm.copy(chosen = false))
            }
        }
    }

    fun findPosition(film: Film): Int? {
        var position: Int? = null
        films.value.forEachIndexed { index, it ->
            if (it.filmId == film.filmId) position = index
        }
        return position
    }

    private fun contains(film: Film, filmList: List<Film>): Boolean {
        filmList.forEach {
            if (it.filmId == film.filmId && it.description == film.description) return true
        }
        return false
    }
}