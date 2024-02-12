package com.illeyrocci.moviesinfo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.illeyrocci.moviesinfo.Model.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM Film")
    fun getChosenFilms(): Flow<List<Film>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChosenFilm(film: Film)

    @Delete
    suspend fun deleteChosenFilm(film: Film)
}
