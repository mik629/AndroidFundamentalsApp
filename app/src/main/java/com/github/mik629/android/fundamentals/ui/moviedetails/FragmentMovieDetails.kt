package com.github.mik629.android.fundamentals.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.network.model.ActorItem
import com.github.mik629.android.fundamentals.data.network.model.MovieDetailsItem
import com.github.mik629.android.fundamentals.databinding.FragmentMovieDetailsBinding
import com.github.mik629.android.fundamentals.ui.global.ActorItemAdapter

class FragmentMovieDetails : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    private val titleToDetailsMap = mapOf(
        "Transformers" to MovieDetailsItem(
            "Transformers",
            "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/77P56ZcL8M9Cw7FIptMIGjhNJoj.jpg",
            "Young teenager, Sam Witwicky becomes involved in the ancient struggle between two extraterrestrial factions of transforming robots – the heroic Autobots and the evil Decepticons. Sam holds the clue to unimaginable power and the Decepticons will stop at nothing to retrieve it.",
            listOf("Adventure", "Action", "Science Fiction"),
            listOf(
                ActorItem(
                    "Shia LaBeouf",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/frYAEeYMi9jdt9MIjGA8vCr5yUC.jpg"
                ),
                ActorItem(
                    "Megan Fox",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/7XkWPXyoByFJorisd1W62uFj0pd.jpg"
                ),
                ActorItem(
                    "John Turturro",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/rNgTqu5ILeUlBLJsDh8mJtpLfOV.jpg"
                ),
                ActorItem(
                    "Tyrese Gibson",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/AlqC7tTsEixGUrBUrKUYzjs78as.jpg"
                )
            ),
            12,
            1878,
            3.5f
        ),
        "Gladiator" to MovieDetailsItem(
            "Gladiator",
            "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/aZtwH3RQ0L8jbInxr7OSc9tlGMJ.jpg",
            "In the year 180, the death of emperor Marcus Aurelius throws the Roman Empire into chaos. Maximus is one of the Roman army's most capable and trusted generals and a key advisor to the emperor. As Marcus' devious son Commodus ascends to the throne, Maximus is set to be executed. He escapes, but is captured by slave traders. Renamed Spaniard and forced to become a gladiator, Maximus must battle to the death with other men for the amusement of paying audiences.",
            listOf("Action", "Drama", "Adventure"),
            listOf(
                ActorItem(
                    "Russell Crowe",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/mGTtPuwE8OR00tkJGmVLJmt8KpW.jpg"
                ),
                ActorItem(
                    "Joaquin Phoenix",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
                ),
                ActorItem(
                    "Connie Nielsen",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/gSQ3O3PJ6ly6nT63joOtfZyscFP.jpg"
                ),
                ActorItem(
                    "Djimon Hounsou",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/kC2AoZV3Wgtm854rEmaMt7YN2i.jpg"
                )
            ),
            16,
            2677,
            4.25f
        ),
        "Toy Story" to MovieDetailsItem(
            "Toy Story",
            "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/3Rfvhy1Nl6sSGJwyjb0QiZzZYlB.jpg",
            "Led by Woody, Andy's toys live happily in his room until Andy's birthday brings Buzz Lightyear onto the scene. Afraid of losing his place in Andy's heart, Woody plots against Buzz. But when circumstances separate Buzz and Woody from their owner, the duo eventually learns to put aside their differences.",
            listOf("Animation", "Adventure", "Family", "Comedy"),
            listOf(
                ActorItem(
                    "Tom Hanks",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/xndWFsBlClOJFRdhSt4NBwiPq2o.jpg"
                ),
                ActorItem(
                    "Tim Allen",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/xjkhfpEBQvXWNT56fSwJDsp140P.jpg"
                ),
                ActorItem(
                    "Don Rickles",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/iJLQV4dcbTUgxlWJakjDldzlMXS.jpg"
                ),
                ActorItem(
                    "Wallace Shawn",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/jviZU3Ae0vVKW6cYeEtjfxq2TWS.jpg"
                )
            ),
            0,
            633,
            4.15f
        ),
        "Pulp Fiction" to MovieDetailsItem(
            "Pulp Fiction",
            "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/w7RDIgQM6bLT7JXtH4iUQd3Iwxm.jpg",
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
            listOf(" Thriller", "Crime"),
            listOf(
                ActorItem(
                    "John Travolta",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/JSt3skdZpGPJYJixCZqH599WdI.jpg"
                ),
                ActorItem(
                    "Uma Thurman",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/tHa7u4ScCm9hwSbwodzj8Jmy3io.jpg"
                ),
                ActorItem(
                    "Tim Roth",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/hgaIXIW9GKnZhrweplBonG7uhhP.jpg"
                ),
                ActorItem(
                    "Harvey Keitel",
                    "http://image.tmdb.org/t/p/w600_and_h900_bestv2/eSZTjD31Uoe1omdc3BThcw1mVU5.jpg"
                )
            ),
            18,
            3109,
            4.45f
        )
    )

    private val glideRequest by lazy {
        GlideApp.with(this)
            .asDrawable()
            .thumbnail(0.1f)
            .placeholder(R.drawable.ic_image_loading)
            .fallback(R.drawable.ic_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
    }

    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        title = (savedInstanceState ?: arguments)?.getString(ARG_TITLE) ?: ""
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        val movieDetailsItem = titleToDetailsMap[title]!!

        with(binding) {
            back.setOnClickListener {
                requireFragmentManager().popBackStack()
            }

            age.text = getString(R.string.movie_min_age, movieDetailsItem.minAge)
            movieTitle.text = movieDetailsItem.title
            genre.text = movieDetailsItem.genres.joinToString()
            ratingLayout.ratingBar.rating = movieDetailsItem.rating
            storyline.text = movieDetailsItem.storyline
            reviews.text = getString(R.string.movie_reviews, movieDetailsItem.reviews)

            val actorItemAdapter = ActorItemAdapter(glideRequest)
            actors.adapter = actorItemAdapter
            actorItemAdapter.submitList(movieDetailsItem.actors)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideRequest.centerCrop()
            .load(titleToDetailsMap[title]!!.background)
            .into(binding.backgroundImg)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_TITLE, title)
    }

    companion object {
        const val ARG_TITLE = "title"

        @JvmStatic
        fun newInstance(title: String) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }
}
