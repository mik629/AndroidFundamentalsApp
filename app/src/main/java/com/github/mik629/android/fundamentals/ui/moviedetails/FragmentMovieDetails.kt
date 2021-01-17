package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.domain.model.MovieItem
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter

class FragmentMovieDetails : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        with(binding) {
            back.setOnClickListener {
                requireFragmentManager().popBackStack()
            }

            movieItem?.let {
                age.text = getString(R.string.movie_min_age, it.minAge)
                movieTitle.text = it.title
                genre.text = it.genres.joinToString { genre -> genre.name }
                ratingLayout.ratingBar.rating = it.rating
                storyline.text = it.overview
                reviews.text = getString(R.string.movie_reviews, it.reviews)
                val actorItemAdapter = ActorItemAdapter(glideRequest)
                actors.adapter = actorItemAdapter
                actorItemAdapter.submitList(it.actors)
            }

            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideRequest.centerCrop()
            .load(movieItem!!.backdrop)
            .into(binding.backgroundImg)
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
