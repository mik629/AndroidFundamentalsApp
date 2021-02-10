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
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter
import com.github.mik629.android.fundamentals.ui.utils.setRating
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val glideRequest by lazy {
        buildGlideRequest(this)
    }

    private lateinit var viewModel: MovieDetailsViewModel

    private var movieId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        movieId = arguments?.getLong(ARG_MOVIE_ID)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        viewModel = AppModule.instance.provideMovieDetailsViewModel(requireContext(), movieId ?: 0)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            it.let {
                binding.age.text = getString(R.string.movie_min_age, it.minAge)
                binding.movieTitle.text = it.title
                binding.genre.text = it.genres.joinToString()
                binding.ratingLayout.setRating(requireContext(), it.rating / 2)
                binding.storyline.text = it.storyline
                binding.reviews.text = getString(R.string.movie_reviews, it.reviews)
                val actorItemAdapter = ActorItemAdapter(glideRequest)
                binding.actors.adapter = actorItemAdapter
                actorItemAdapter.submitList(it.actors)
                if (!it.background.isNullOrEmpty()) {
                    glideRequest.centerCrop()
                        .load("${BuildConfig.BASE_IMAGE_URL}${it.background}")
                        .into(binding.backgroundImg)
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_no_data),
                BaseTransientBottomBar.LENGTH_LONG
            )
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
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
