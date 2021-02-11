package com.github.mik629.android.fundamentals.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.di.AppModule
import com.github.mik629.android.fundamentals.di.buildGlideRequest
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {
    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesListViewModel by viewModels(
        factoryProducer = {
            MoviesListViewModelFactory(requireContext())
        }
    )

    private val movieItemAdapter by lazy {
        MovieItemAdapter(
            { movie ->
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(FragmentMoviesList::class.qualifiedName) // fixme add cicerone
                    .add(R.id.main_container, FragmentMovieDetails.newInstance(movie.id))
                    .commit()
            },
            buildGlideRequest(this)
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.movies.observe(this@FragmentMoviesList.viewLifecycleOwner) {
            movieItemAdapter.submitList(it)
        }
        viewModel.error.observe(this@FragmentMoviesList.viewLifecycleOwner) {
            Snackbar.make(binding.root, getString(R.string.error_no_data), LENGTH_LONG)
                .show()
        }
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
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}

private class MoviesListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppModule.instance.provideMovieListViewModel(context) as T
    }
}
