package com.github.mik629.android.fundamentals.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.loadMovies
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding

    private val scope = CoroutineScope(Dispatchers.Main)

    private val movieItemAdapter by lazy {
        MovieItemAdapter(
            { movieTitle ->
                requireFragmentManager()
                    .beginTransaction()
                    .addToBackStack(FragmentMoviesList::class.simpleName)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesListBinding.inflate(layoutInflater)

        with(binding) {
            movieList.adapter = movieItemAdapter
            scope.launch {
                movieItemAdapter.submitList(
                    loadMovies(requireContext())
                )
            }

        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
