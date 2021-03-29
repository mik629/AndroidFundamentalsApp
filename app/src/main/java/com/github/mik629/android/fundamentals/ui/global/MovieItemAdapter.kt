package com.github.mik629.android.fundamentals.ui.global

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.MovieItemBinding
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.ui.utils.setRating

class MovieItemAdapter(
    private val clickListener: (Movie) -> Unit,
    private val glideRequest: GlideRequest<Drawable>
) : ListAdapter<Movie, MovieItemAdapter.ViewHolder>(MovieItemAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            glideRequest,
            clickListener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateViewItem(getItem(position))
    }

    class ViewHolder(
        private val binding: MovieItemBinding,
        private val glideRequest: GlideRequest<Drawable>,
        private val clickListener: (Movie) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateViewItem(item: Movie) {
            val rootView = binding.root
            rootView.setOnClickListener { clickListener(item) }
            if (!item.posterUrl.isNullOrEmpty()) {
                glideRequest.fitCenter()
                    .load(item.posterUrl)
                    .into(binding.moviePoster)
            } else {
                binding.moviePoster.setImageResource(R.drawable.ic_broken_image) // or som better image
            }
            val resources = rootView.resources
            binding.minAge.text = resources.getString(R.string.movie_min_age, item.minAge)
            binding.movieTitle.text = item.title
            binding.genres.text = item.genres.joinToString { it.name }
            binding.ratingLayout.setRating(rootView.context, item.rating / 2)
            binding.reviews.text = resources.getString(R.string.movie_reviews, item.reviews)
            binding.movieLength.text = resources.getString(R.string.movie_length, item.runtime)
        }
    }
}

private class MovieItemAdapterDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        (oldItem.id) == (newItem.id)

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}