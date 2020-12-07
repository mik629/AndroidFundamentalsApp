package com.github.mik629.android.fundamentals.ui.global

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.data.network.model.ActorItem
import com.github.mik629.android.fundamentals.databinding.ActorItemBinding

class ActorItemAdapter(
    private val glideRequest: GlideRequest<Drawable>
) : ListAdapter<ActorItem, ActorItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateViewItem(getItem(position))
    }

    inner class ViewHolder(private val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateViewItem(item: ActorItem) {
            with(binding) {
                name.text = item.name
                glideRequest.fitCenter()
                    .load(item.ava)
                    .into(ava)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ActorItem>() {
            override fun areItemsTheSame(oldItem: ActorItem, newItem: ActorItem): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ActorItem, newItem: ActorItem): Boolean =
                oldItem == newItem

        }
    }
}