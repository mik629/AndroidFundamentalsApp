package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : Fragment() {
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesDetailsBinding.inflate(layoutInflater)

        binding.back.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .remove(this)
                .commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesDetails()
    }
}
