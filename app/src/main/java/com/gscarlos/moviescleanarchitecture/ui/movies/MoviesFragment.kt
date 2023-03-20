package com.gscarlos.moviescleanarchitecture.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.databinding.FragmentMoviesBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.ui.movies.adapter.MoviesAdapterEvent
import com.gscarlos.moviescleanarchitecture.ui.movies.adapter.MoviesHorizontalAdapter
import com.gscarlos.moviescleanarchitecture.ui.movies.adapter.MoviesVerticalAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapterPopular: MoviesHorizontalAdapter
    private lateinit var adapterMostRated: MoviesHorizontalAdapter
    private lateinit var adapterRecommended: MoviesVerticalAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initElements()
        initUiState()
        viewModel.loadMovies()
    }

    private fun initElements() {

        adapterPopular = MoviesHorizontalAdapter {
            when(it) {
                is MoviesAdapterEvent.OnFavorite -> onFavorite(it.movie)
                is MoviesAdapterEvent.OnItem -> openDetail(it.movie)
            }

        }
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviesPopular.layoutManager = layoutManager
        binding.rvMoviesPopular.adapter = adapterPopular

        adapterMostRated = MoviesHorizontalAdapter {
            when(it) {
                is MoviesAdapterEvent.OnFavorite -> onFavorite(it.movie)
                is MoviesAdapterEvent.OnItem -> openDetail(it.movie)
            }

        }
        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviesMostRated.layoutManager = layoutManager2
        binding.rvMoviesMostRated.adapter = adapterMostRated


        adapterRecommended = MoviesVerticalAdapter {
            when(it) {
                is MoviesAdapterEvent.OnFavorite -> onFavorite(it.movie)
                is MoviesAdapterEvent.OnItem -> openDetail(it.movie)
            }

        }
        binding.rvMoviesReccomended.adapter = adapterRecommended
    }

    private fun onFavorite(movie: MovieToShow) {
        viewModel.onFavorite(movie)
    }

    private fun initUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                when (state) {
                    MoviesViewState.Start -> {}
                    MoviesViewState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(
                            binding.root.context,
                            getString(R.string.error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    MoviesViewState.Loading -> binding.pbLoading.visibility = View.VISIBLE
                    MoviesViewState.Success -> binding.pbLoading.visibility = View.GONE

                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMoviesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collectLatest {
                adapterPopular.submitList(it)
                binding.tvPopular.visibility = View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mostRatedMoviesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collectLatest {
                adapterMostRated.submitList(it)
                binding.tvMostRated.visibility = View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recommendedMoviesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collectLatest {
                adapterRecommended.submitList(it)
                binding.tvRecommended.visibility = View.VISIBLE
            }
        }
    }

    private fun openDetail(movieToShow: MovieToShow) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}