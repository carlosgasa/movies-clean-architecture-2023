package com.gscarlos.moviescleanarchitecture.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.databinding.FragmentMoviesBinding
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter


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
        adapter = MoviesAdapter {
            openDetail(it)
        }
        binding.rvMovies.adapter = adapter
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
            viewModel.moviesState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collectLatest {
                adapter.submitList(it)
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