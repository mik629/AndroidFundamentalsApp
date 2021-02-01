package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.domain.model.MovieItem
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter
import com.github.mik629.android.fundamentals.ui.utils.setRating

class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val glideRequest by lazy {
        GlideApp.with(this)
            .asDrawable()
            .thumbnail(0.1f)
            .placeholder(R.drawable.ic_image_loading)
            .fallback(R.drawable.ic_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
    }

    private var movieItem: MovieItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        movieItem = (savedInstanceState ?: arguments)?.getParcelable(ARG_MOVIE_ITEM)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            back.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            movieItem?.let {
                age.text = getString(R.string.movie_min_age, it.minAge)
                movieTitle.text = it.title
                genre.text = it.genres.joinToString { genre -> genre.name }
                ratingLayout.setRating(requireContext(), it.rating / 2)
                storyline.text = it.overview
                reviews.text = getString(R.string.movie_reviews, it.reviews)
                val actorItemAdapter = ActorItemAdapter(glideRequest)
                actors.adapter = actorItemAdapter
                actorItemAdapter.submitList(it.actors)
            }
        }

        movieItem?.let {
            if (!it.backdrop.isNullOrEmpty()) {
                glideRequest.centerCrop()
                    .load("${BuildConfig.BASE_IMAGE_URL}${it.backdrop}")
                    .into(binding.backgroundImg)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_MOVIE_ITEM, movieItem)
    }

    companion object {
        const val ARG_MOVIE_ITEM = "movieItem"

        @JvmStatic
        fun newInstance(movieItem: MovieItem) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_MOVIE_ITEM, movieItem)
            }
        }
    }
}
