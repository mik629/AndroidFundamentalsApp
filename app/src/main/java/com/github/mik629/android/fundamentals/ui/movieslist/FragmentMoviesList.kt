package com.github.mik629.android.fundamentals.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.network.MovieItem
import com.github.mik629.android.fundamentals.databinding.FragmentMoviesListBinding
import com.github.mik629.android.fundamentals.ui.global.MovieItemAdapter
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMoviesDetails

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesListBinding.inflate(layoutInflater)

        with(binding) {
            val movieItemAdapter = MovieItemAdapter {
                requireFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.main_container, FragmentMoviesDetails.newInstance())
                    .commit()
            }
            movieList.adapter = movieItemAdapter
            movieItemAdapter.submitList(
                listOf(
                    // fixme: remove hardcode when data source is known
                    MovieItem(
                        "Avengers: End Game",
                        listOf("Action", "Adventure", "Fantasy"),
                        13, 125, 4f, 137
                    )
                )
            )
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()
    }
}
