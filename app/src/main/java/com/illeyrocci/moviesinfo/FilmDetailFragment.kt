package com.illeyrocci.moviesinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.illeyrocci.moviesinfo.databinding.FragmentFilmDetailBinding
import okhttp3.Interceptor.*

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val args: FilmDetailFragmentArgs by navArgs()
    private val filmDetailViewModel: FilmDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.filmId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding =
            FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val tempFilm = filmDetailViewModel.film
            if (tempFilm == null) {
                filmTitle.text = "пусто"
            } else {
                filmTitle.text = tempFilm.nameRu
                year.text = tempFilm.year.toString()
                context?.let {
                    Glide.with(it)
                        .load(tempFilm.posterUrlPreview)
                        .into(imageView)
                }
                description.text = tempFilm.description
                genres.text = tempFilm.genres?.fold("") { acc, genre -> "$acc, ${genre.genre}" } ?: ""
                country.text = tempFilm.countries?.fold("") { acc, country -> "$acc, ${country.country}" } ?: ""
            }
            back.setOnClickListener {
                findNavController().navigate(FilmDetailFragmentDirections.backToList())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}