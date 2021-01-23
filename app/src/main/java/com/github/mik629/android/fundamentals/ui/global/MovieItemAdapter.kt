package com.github.mik629.android.fundamentals.ui.global

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.MovieItemBinding
import com.github.mik629.android.fundamentals.domain.model.MovieItem

class MovieItemAdapter(
    private val clickListener: (MovieItem) -> Unit,
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
                root.setOnClickListener { clickListener(item) }
                if (!item.poster.isNullOrEmpty()) {
                    val imageUrl = "${BuildConfig.BASE_IMAGE_URL}${item.poster}"
                    glideRequest.fitCenter()
                        .load(imageUrl)
                        .into(moviePoster)
//                    Timber.i("Trying to load image $imageUrl")
                }
                minAge.text = root.resources.getString(R.string.movie_min_age, item.minAge)
                movieTitle.text = item.title
                genres.text = item.genres.joinToString { it.name }
                ratingLayout.ratingBar.rating = item.rating / 2
                reviews.text = root.resources.getString(R.string.movie_reviews, item.reviews)
                movieLength.text = root.resources.getString(R.string.movie_length, item.runtime)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
                oldItem == newItem
        }
    }
}