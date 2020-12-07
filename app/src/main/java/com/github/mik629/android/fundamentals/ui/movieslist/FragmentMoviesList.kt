package com.github.mik629.android.fundamentals.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.network.model.MovieItem
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding

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

    private val movies by lazy {
        listOf(
            MovieItem(
                "Transformers",
                "http://image.tmdb.org/t/p/w600_and_h900_bestv2/6eehp9I54syN3x753XMqjKz8M3F.jpg",
                listOf("Adventure", "Action", "Science Fiction"),
                12,
                1878,
                7f,
                144
            ),
            MovieItem(
                "Gladiator",
                "http://image.tmdb.org/t/p/w600_and_h900_bestv2/pRn3TJHbAqCAO6U8Dw5DayVUuX3.jpg",
                listOf("Action", "Drama", "Adventure"),
                16,
                2677,
                8.5f,
                155
            ),
            MovieItem(
                "Toy Story",
                "http://image.tmdb.org/t/p/w600_and_h900_bestv2/rTtFXrAIw0nZJxh6EhBhASrhrU3.jpg",
                listOf("Animation", "Adventure", "Family", "Comedy"),
                0,
                633,
                8.3f,
                81
            ),
            MovieItem(
                "Pulp Fiction",
                "http://image.tmdb.org/t/p/w600_and_h900_bestv2/dRZpdpKLgN9nk57zggJCs1TjJb4.jpg",
                listOf("Thriller", "Crime"),
                18,
                3109,
                8.9f,
                154
            )
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
            movieItemAdapter.submitList(movies)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
