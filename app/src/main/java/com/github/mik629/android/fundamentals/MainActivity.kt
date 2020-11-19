package com.github.mik629.android.fundamentals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mik629.android.fundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.movieDetailsBtn.setOnClickListener { navigateToMovieDetailsScreen() }
    }

    private fun navigateToMovieDetailsScreen() {
        startActivity(
            MovieDetailsActivity.createIntent(this)
        )
    }
}
