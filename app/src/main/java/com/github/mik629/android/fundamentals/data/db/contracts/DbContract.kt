package com.github.mik629.android.fundamentals.data.db.contracts

object DbContract {
    object Actors {
        const val ACTORS_TABLE_NAME = "actors"

        const val COLUMN_NAME_ACTOR_ID = "actorId"
    }

    object Genres {
        const val COLUMN_NAME_GENRE_ID = "genreId"

        const val GENRES_TABLE_NAME = "genres"
    }

    object Movies {
        const val MOVIES_DB_NAME = "Movies.db"

        const val MOVIES_TABLE_NAME = "movies"

        const val COLUMN_NAME_RATING = "rating"

        const val COLUMN_NAME_MOVIE_ID = "movieId"
    }
}