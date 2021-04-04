package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.appComponent
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter
import com.github.mik629.android.fundamentals.ui.utils.buildGlideRequest
import com.github.mik629.android.fundamentals.ui.utils.setRating
import com.github.mik629.android.fundamentals.ui.utils.showSnackBar
import javax.inject.Inject

class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    @Inject
    lateinit var movieDetailsViewModelFactory: MovieDetailsViewModelFactory.Factory

    private val glideRequest by lazy {
        buildGlideRequest(this)
    } // stop storing link on this
    private val actorItemAdapter by lazy {
        ActorItemAdapter(glideRequest)
    }

    private val viewModel: MovieDetailsViewModel by viewModels(
        factoryProducer = {
            movieDetailsViewModelFactory.create(
                id = arguments?.getLong(ARG_MOVIE_ID) ?: -1
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actors.adapter = actorItemAdapter
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            setMovieDetailsData(movieDetails)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.root.showSnackBar(message = getString(R.string.error_no_data))
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
                .load(movieDetails.backdropImageUrl)
                .into(binding.backgroundImg)
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movieId"

        @JvmStatic
        fun newInstance(movieId: Long): FragmentMovieDetails {
            val fragment = FragmentMovieDetails()
            val args = Bundle(1)
            args.putLong(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}
