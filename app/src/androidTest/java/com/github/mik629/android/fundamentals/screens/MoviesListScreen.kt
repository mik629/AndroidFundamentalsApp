package com.github.mik629.android.fundamentals.screens

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher

object MoviesListScreen: KScreen<MoviesListScreen>() {
    override val layoutId: Int = R.layout.fragment_movies_list
    override val viewClass: Class<*> = FragmentMoviesList::class.java

    val movieList: KRecyclerView = KRecyclerView(
        builder = { withId(R.id.movie_list) },
        itemTypeBuilder = { itemType(::MovieItem) }
    )

    class MovieItem(parent: Matcher<View>) : KRecyclerItem<MovieItem>(parent) {
        val moviePoster: KImageView = KImageView(parent) { withId(R.id.movie_poster) }
        val minAge: KTextView = KTextView(parent) { withId(R.id.min_age) }
        val genres: KTextView = KTextView(parent) { withId(R.id.genres) }
        val reviews: KTextView = KTextView(parent) { withId(R.id.reviews) }
        val movieTitle: KTextView = KTextView(parent) { withId(R.id.movie_title) }
        val movieLength: KTextView = KTextView(parent) { withId(R.id.movie_length) }
    }
}
