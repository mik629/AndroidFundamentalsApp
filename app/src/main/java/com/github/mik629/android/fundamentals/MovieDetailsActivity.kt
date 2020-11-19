package com.github.mik629.android.fundamentals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mik629.android.fundamentals.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MovieDetailsActivity::class.java)
    }
}
