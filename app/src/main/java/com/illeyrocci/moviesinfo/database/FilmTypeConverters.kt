package com.illeyrocci.moviesinfo.database

import androidx.room.TypeConverter
import com.illeyrocci.moviesinfo.Model.Country
import com.illeyrocci.moviesinfo.Model.Genre

class FilmTypeConverters {
    @TypeConverter
    fun fromCountries(countries: ArrayList<Country>): String {
        var str = ""
        countries.forEachIndexed { index, it ->
            str += it.country
            if (index != countries.size - 1) str += ", "
        }
        return str
    }

    @TypeConverter
    fun toCountries(country: String?): ArrayList<Country>? {
        val s = country?.split(", ")
        val arlst: ArrayList<Country> = arrayListOf()
        s?.forEach { arlst.add(Country(it)) }
        return arlst
    }

    @TypeConverter
    fun fromGenres(genres: ArrayList<Genre>): String {
        var str = ""
        genres.forEachIndexed { index, it ->
            str += it.genre
            if (index != genres.size - 1) str += ", "
        }
        return str
    }

    @TypeConverter
    fun toGenres(genre: String?): ArrayList<Genre>? {
        val s = genre?.split(", ")
        val arlst: ArrayList<Genre> = arrayListOf()
        s?.forEach { arlst.add(Genre(it)) }
        return arlst
    }
}
