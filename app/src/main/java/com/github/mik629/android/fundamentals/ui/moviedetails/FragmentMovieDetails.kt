package com.github.mik629.android.fundamentals.ui.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    private val actorItemAdapter by lazy {
        ActorItemAdapter(glideRequest)
    }

    private var movieId: Long = 0

    private val viewModel: MovieDetailsViewModel by viewModels(
        factoryProducer = {
            MovieDetailsViewModelFactory(requireContext(), movieId)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getLong(ARG_MOVIE_ID) ?: 0
    }

    override fun onStart() {
        super.onStart()
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            it.let {
                binding.age.text = getString(R.string.movie_min_age, it.minAge)
                binding.movieTitle.text = it.title
                binding.genre.text = it.genres.joinToString()
                binding.ratingLayout.setRating(requireContext(), it.rating / 2)
                binding.storyline.text = it.storyline
                binding.reviews.text = getString(R.string.movie_reviews, it.reviews)
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
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actors.adapter = actorItemAdapter
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
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

private class MovieDetailsViewModelFactory(
    private val context: Context,
    private val movieId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppModule.instance.provideMovieDetailsViewModel(
            context,
            movieId
        ) as T
    }
}
