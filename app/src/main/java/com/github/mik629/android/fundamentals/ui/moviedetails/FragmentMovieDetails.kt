package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter

class FragmentMovieDetails : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        with(binding) {
            back.setOnClickListener {
                requireFragmentManager().beginTransaction()
                    .remove(this@FragmentMovieDetails)
                    .commit()
            }
            val actorItemAdapter = ActorItemAdapter()
            actors?.adapter = actorItemAdapter
            actorItemAdapter.submitList(
                listOf(
//                    ActorItem(
//
//                    )
                )
            )
            return root
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMovieDetails()
    }
}
