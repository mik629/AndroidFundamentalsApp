package com.github.mik629.android.fundamentals.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList

class AppActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentMoviesList.newInstance())
                .commit()
        }
    }
}