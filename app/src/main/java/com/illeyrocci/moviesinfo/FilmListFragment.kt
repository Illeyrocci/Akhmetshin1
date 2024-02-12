package com.illeyrocci.moviesinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.illeyrocci.moviesinfo.Model.Country
import com.illeyrocci.moviesinfo.Model.Film
import com.illeyrocci.moviesinfo.Model.Genre
import com.illeyrocci.moviesinfo.databinding.FragmentFilmListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random


class FilmListFragment : Fragment() {
    private var _binding: FragmentFilmListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val filmListViewModel: FilmListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmListBinding.inflate(inflater, container, false)
        binding.filmRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonChosen.setOnClickListener {
                filmListViewModel._inChosenList.update { true }
                binding.listTitle.text = getString(R.string.title_chosen)
            }

            buttonPopular.setOnClickListener {
                filmListViewModel._inChosenList.update { false }
                binding.listTitle.text = getString(R.string.title_popular)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                filmListViewModel.films.collect { films ->
                    val lambdaNavigate = { filmId: Int ->
                        findNavController().navigate(FilmListFragmentDirections.toFilmDetails(filmId))
                    }
                    binding.movieRecyclerViewShimmer.startShimmerAnimation()
                    binding.movieRecyclerViewShimmer.visibility = View.VISIBLE
                    binding.filmRecyclerView.visibility = View.GONE

                    try {
                        binding.filmRecyclerView.adapter =
                            FilmListAdapter(films, lambdaNavigate) { newFilm ->
                                filmListViewModel.updateFilm(newFilm)
                                binding.filmRecyclerView.scrollToPosition(
                                    filmListViewModel.findPosition(
                                        newFilm
                                    ) ?: 0
                                )
                            }
                    } catch (e:Exception) {
                        binding.filmRecyclerView.visibility = View.GONE
                        binding.errorLayout.root.visibility = View.VISIBLE
                        binding.errorLayout.repeat.setOnClickListener {
                            requireActivity().supportFragmentManager.apply {
                                commit {
                                    setReorderingAllowed(true)
                                    detach(FilmListFragment())
                                }
                                executePendingTransactions()
                                commit {
                                    setReorderingAllowed(true)
                                    attach(FilmListFragment())
                                }
                            }
                        }
                    }
                    binding.movieRecyclerViewShimmer.stopShimmerAnimation()
                    binding.movieRecyclerViewShimmer.visibility = View.GONE
                    binding.filmRecyclerView.visibility = View.VISIBLE

                }
            }
        }

        binding.filmRecyclerView.addItemDecoration(
            SpaceItemDecoration(32)
        )


    }

    override fun onStart() {
        super.onStart()
        binding.movieRecyclerViewShimmer.startShimmerAnimation()
    }
    override fun onPause() {
        binding.movieRecyclerViewShimmer.stopShimmerAnimation()
        super.onPause()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}