package com.github.mik629.android.fundamentals.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.appComponent
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.ui.ViewState
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.github.mik629.android.fundamentals.ui.utils.showSnackBar
import javax.inject.Inject

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {
    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    @Inject
    lateinit var moviesListViewModelFactory: MoviesListViewModel.Factory

    private val viewModel: MoviesListViewModel by viewModels(
        factoryProducer = { moviesListViewModelFactory }
    )

    private val movieItemAdapter by lazy {
        MovieItemAdapter { movie ->
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(FragmentMoviesList::class.qualifiedName) // fixme add cicerone
                .add(R.id.main_container, FragmentMovieDetails.newInstance(movie.id))
                .commit()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieList.adapter = movieItemAdapter
        binding.movieList.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.span_count),
            resources.getInteger(R.integer.scroll_orientation),
            false
        )

        viewModel.movies.observe(this@FragmentMoviesList.viewLifecycleOwner) { movies ->
            when (movies) {
                is ViewState.Loading -> showLoading(isLoading = true)
                is ViewState.Success -> {
                    showLoading(isLoading = false)
                    movieItemAdapter.submitList(movies.result)
                }
                is ViewState.Error -> {
                    showLoading(isLoading = false)
                    binding.root.showSnackBar(message = getString(R.string.error_no_data))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.movieList.isVisible = !isLoading
        binding.progressbar.isVisible = isLoading
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
