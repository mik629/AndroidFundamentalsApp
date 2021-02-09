package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.di.AppModule
import com.github.mik629.android.fundamentals.di.buildGlideRequest
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter
import com.github.mik629.android.fundamentals.ui.utils.setRating

class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val glideRequest by lazy {
        buildGlideRequest(this)
    }

    private val viewModel by lazy {
        AppModule.instance.provideViewModel(requireContext())
    }

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        movie = arguments?.getString(ARG_MOVIE_ID)
            ?.let { viewModel.getMovie(it) }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        movie?.let {
            binding.age.text = getString(R.string.movie_min_age, it.minAge)
            binding.movieTitle.text = it.title
            binding.genre.text = it.genres.joinToString { genre -> genre.name }
            binding.ratingLayout.setRating(requireContext(), it.rating / 2)
            binding.storyline.text = it.overview
            binding.reviews.text = getString(R.string.movie_reviews, it.reviews)
            val actorItemAdapter = ActorItemAdapter(glideRequest)
            binding.actors.adapter = actorItemAdapter
            actorItemAdapter.submitList(it.actors)
        }

        movie?.let {
            if (!it.backdrop.isNullOrEmpty()) {
                glideRequest.centerCrop()
                    .load("${BuildConfig.BASE_IMAGE_URL}${it.backdrop}")
                    .into(binding.backgroundImg)
            }
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movieId"

        @JvmStatic
        fun newInstance(movieId: String): FragmentMovieDetails {
            val fragment = FragmentMovieDetails()
            val args = Bundle(1)
            args.putString(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}
