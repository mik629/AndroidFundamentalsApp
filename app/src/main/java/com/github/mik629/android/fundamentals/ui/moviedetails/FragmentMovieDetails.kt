package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.appComponent
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.ui.BaseFragment
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter
import com.github.mik629.android.fundamentals.ui.utils.buildGlideRequest
import com.github.mik629.android.fundamentals.ui.utils.setRating
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val glideRequest by lazy {
        buildGlideRequest(this)
    }
    private val actorItemAdapter by lazy {
        ActorItemAdapter(glideRequest)
    }

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        arguments?.getLong(ARG_MOVIE_ID)
            ?.also { id -> viewModel.setId(id = id) }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actors.adapter = actorItemAdapter
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            setMovieDetailsData(movieDetails)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_no_data),
                BaseTransientBottomBar.LENGTH_LONG
            ).show()
        }
    }

    private fun setMovieDetailsData(movieDetails: Movie) {
        binding.age.text = getString(R.string.movie_min_age, movieDetails.minAge)
        binding.movieTitle.text = movieDetails.title
        binding.genre.text = movieDetails.genres.joinToString()
        binding.ratingLayout.setRating(requireContext(), movieDetails.rating / 2)
        binding.storyline.text = movieDetails.overview
        binding.reviews.text = getString(R.string.movie_reviews, movieDetails.reviews)
        actorItemAdapter.submitList(movieDetails.actors)
        if (!movieDetails.backdropImageUrl.isNullOrEmpty()) {
            glideRequest.centerCrop()
                .load("${BuildConfig.BASE_IMAGE_URL}${movieDetails.backdropImageUrl}")
                .into(binding.backgroundImg)
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movieId"

        @JvmStatic
        fun newInstance(movieId: Long): FragmentMovieDetails {
            val fragment = FragmentMovieDetails()
            val args = Bundle()
            args.putLong(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}
