package com.github.mik629.android.fundamentals.screens

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher

object MovieDetailsScreen: KScreen<MovieDetailsScreen>() {
    override val layoutId: Int = R.layout.fragment_movie_details
    override val viewClass: Class<*> = FragmentMovieDetails::class.java

    val backgroundImg: KImageView = KImageView { withId(R.id.background_img) }
    val back: KTextView = KTextView { withId(R.id.back) }
    val age: KTextView = KTextView { withId(R.id.age) }
//    val movieTitle: KTextView = KTextView { withId(R.id.movie_title) } fixme: duplicate id
    val genre: KTextView = KTextView { withId(R.id.genre) }
//    val reviews: KTextView = KTextView { withId(R.id.reviews) } fixme: duplicate id
    val storylineTitle: KTextView = KTextView { withId(R.id.storyline_title) }
    val storyline: KTextView = KTextView { withId(R.id.storyline) }
    val cast: KTextView = KTextView { withId(R.id.cast) }
    val actors: KRecyclerView = KRecyclerView(
        builder = { withId(R.id.actors) },
        itemTypeBuilder = { itemType(::ActorItem) }
    )

    class ActorItem(parent: Matcher<View>) : KRecyclerItem<ActorItem>(parent) {
        val avatar: KImageView = KImageView(parent) { withId(R.id.avatar) }
        val name: KTextView = KTextView(parent) { withId(R.id.name) }
    }
}