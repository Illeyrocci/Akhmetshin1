package com.illeyrocci.moviesinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.illeyrocci.moviesinfo.Model.Film
import com.illeyrocci.moviesinfo.databinding.ListItemFilmBinding

class FilmHolder(
    private val binding: ListItemFilmBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        film: Film,
        onFilmClicked: (Int) -> Unit,
        onFilmLongClicked: (Film) -> Unit
    ) {
        binding.itemTitle.text = film.nameRu

        Glide.with(context)
            .load(film.posterUrlPreview)
            .into(binding.itemFilmPoster)

        binding.genreYear.text = context.getString(
            R.string.genre_and_year,
            film.genres?.get(0)?.genre ?: "неизвестный жанр",
            film.year
        )

        binding.root.setOnClickListener {
            onFilmClicked(film.filmId)
        }

        setVisibility(film.chosen)

        binding.root.setOnLongClickListener {
            val isChosen = film.chosen
            setVisibility(!isChosen)
            onFilmLongClicked(film.copy(chosen = !isChosen))
            return@setOnLongClickListener true
        }
    }

    private fun setVisibility(condition: Boolean) {
        if (condition) binding.starIcon.visibility =
            View.VISIBLE else View.INVISIBLE
    }
}

class FilmListAdapter(
    var films: List<Film>,
    private val onFilmClicked: (Int) -> Unit,
    private val onFilmLongClicked: (Film) -> Unit
) : RecyclerView.Adapter<FilmHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFilmBinding.inflate(inflater, parent, false)
        return FilmHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val film = films[position]
        holder.bind(film, onFilmClicked, onFilmLongClicked)
    }

}