package com.illeyrocci.moviesinfo.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Film(
    @PrimaryKey val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val reviewsCount: Int?,
    val ratingGoodReview: Double?,
    val ratingGoodReviewVoteCount: Int?,
    val ratingKinopoisk: Double?,
    val ratingKinopoiskVoteCount: Int?,
    val ratingImdb: Double?,
    val ratingImdbVoteCount: Int?,
    val ratingFilmCritics: Double?,
    val ratingFilmCriticsVoteCount: Int?,
    val ratingRfCritics: Double?,
    val ratingRfCriticsVoteCount: Int?,
    val webUrl: String?,
    val year: Int?,
    val filmLength: String?,
    val slogan: String?,
    val description: String?,
    val productionStatus: String?,
    val type: String?,
    val ratingAgeLimits: String?,
    val hasImax: Boolean?,
    val has3D: Boolean?,
    val countries: ArrayList<Country>?,
    val genres: ArrayList<Genre>?,

    val chosen: Boolean
)
//class Film {
//    var kinopoiskId: Int? = null
//    var nameRu: String? = null
//    var nameEn: String? = null
//    var posterUrl: String? = null
//    var posterUrlPreview: String? = null
//    var reviewsCount: Int? = null
//    var ratingGoodReview: Double? = null
//    var ratingGoodReviewVoteCount: Int? = null
//    var ratingKinopoisk: Double? = null
//    var ratingKinopoiskVoteCount: Int? = null
//    var ratingImdb: Double? = null
//    var ratingImdbVoteCount: Int? = null
//    var ratingFilmCritics: Double? = null
//    var ratingFilmCriticsVoteCount: Int? = null
//    var ratingRfCritics: Double? = null
//    var ratingRfCriticsVoteCount: Int? = null
//    var webUrl: String? = null
//    var year: Int? = null
//    var filmLength: String? = null
//    var slogan: String? = null
//    var description: String? = null
//    var productionStatus: String? = null
//    var type: String? = null
//    var ratingAgeLimits: String? = null
//    var hasImax: Boolean? = null
//    var has3D: Boolean? = null
//    var countries: ArrayList<Country>? = null
//    var genres: ArrayList<Genre>? = null
//}
class Country() {
    var country: String? = null
    constructor(country: String?) : this() {
        this.country = country
    }
}

class Genre() {
    var genre: String? = null
    constructor(genre: String?) : this() {
        this.genre = genre
    }
}