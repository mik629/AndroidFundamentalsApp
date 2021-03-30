package com.github.mik629.android.fundamentals.ui.movieslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.appComponent
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.github.mik629.android.fundamentals.ui.utils.buildGlideRequest
import com.github.mik629.android.fundamentals.ui.utils.showSnackBar

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {
    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesListViewModel by viewModels(
        factoryProducer = { appComponent.provideMoviesListViewModelFactory() }
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
            movieItemAdapter.submitList(movies)
        }
        viewModel.error.observe(this@FragmentMoviesList.viewLifecycleOwner) {
            binding.root.showSnackBar(message = getString(R.string.error_no_data))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
