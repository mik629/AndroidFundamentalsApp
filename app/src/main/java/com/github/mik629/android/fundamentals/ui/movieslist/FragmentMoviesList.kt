package com.github.mik629.android.fundamentals.ui.movieslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.di.AppModule
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {
    private val binding by viewBinding(FragmentMoviesListBinding::bind)

    // fixme add factory
    private val viewModel by lazy {
        MoviesListViewModel(
            MoviesRepositoryImpl(
                AppModule().retrofit.create(ServerApi::class.java),
                MovieDb.createDb(requireContext()).dao
            )
        )
    }

    private val movieItemAdapter by lazy {
        MovieItemAdapter(
            { movieTitle ->
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(FragmentMoviesList::class.qualifiedName) // fixme add cicerone
                    .add(R.id.main_container, FragmentMovieDetails.newInstance(movieTitle))
                    .commit()
            },
            GlideApp.with(this)
                .asDrawable()
                .thumbnail(0.1f)
                .placeholder(R.drawable.ic_image_loading)
                .fallback(R.drawable.ic_broken_image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieList.adapter = movieItemAdapter
        viewModel.movies.observe(this@FragmentMoviesList.viewLifecycleOwner) {
            movieItemAdapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
