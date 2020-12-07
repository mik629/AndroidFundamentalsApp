package com.github.mik629.android.fundamentals.ui.global

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.network.model.MovieItem
import com.github.mik629.android.fundamentals.databinding.MovieItemBinding

class MovieItemAdapter(
    private val clickListener: (String) -> Unit,
    private val glideRequest: GlideRequest<Drawable>
) : ListAdapter<MovieItem, MovieItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateViewItem(getItem(position))
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateViewItem(item: MovieItem) {
            with(binding) {
                root.setOnClickListener { clickListener(item.title) }
                glideRequest.fitCenter()
                    .load(item.poster)
                    .into(moviePoster)
                minAge.text = root.resources.getString(R.string.movie_min_age, item.minAge)
                movieTitle.text = item.title
                genres.text = item.genres.joinToString()
                ratingLayout.ratingBar.rating = item.rating
                reviews.text = root.resources.getString(R.string.movie_reviews, item.reviews)
                movieLength.text = root.resources.getString(R.string.movie_length, item.minutes)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem == newItem
        }
    }
}