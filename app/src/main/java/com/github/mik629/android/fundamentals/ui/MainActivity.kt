package com.github.mik629.android.fundamentals.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.databinding.ActivityMainBinding
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, FragmentMoviesList.newInstance())
            .commit()
    }
}
