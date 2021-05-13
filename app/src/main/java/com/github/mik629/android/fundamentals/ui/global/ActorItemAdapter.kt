package com.github.mik629.android.fundamentals.ui.global

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.ActorItemBinding
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.ui.utils.buildGlideRequest

class ActorItemAdapter :
    ListAdapter<Actor, ActorItemAdapter.ViewHolder>(ActorItemAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateViewItem(getItem(position))
    }

    class ViewHolder(
        private val binding: ActorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val glideRequest by lazy {
            buildGlideRequest(binding.root.context)
        }

        fun updateViewItem(item: Actor) {
            binding.name.text = item.name
            if (!item.photoUrl.isNullOrEmpty()) {
                glideRequest.centerCrop()
                    .load(item.photoUrl)
                    .into(binding.avatar)
            } else {
                binding.avatar.setImageResource(R.drawable.ic_broken_image) // or som better image
            }
        }
    }
}

private class ActorItemAdapterDiffCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem == newItem
}
