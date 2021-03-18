package com.github.mik629.android.fundamentals.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.appComponent
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentMoviesList.newInstance())
                .commit()
        }
    }
}
