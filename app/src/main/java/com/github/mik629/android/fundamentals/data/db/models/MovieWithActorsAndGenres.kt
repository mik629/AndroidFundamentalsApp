package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Actors.COLUMN_NAME_ACTOR_ID
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Genres.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.COLUMN_NAME_MOVIE_ID

data class MovieWithActorsAndGenres(
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = COLUMN_NAME_MOVIE_ID,
        entityColumn = COLUMN_NAME_ACTOR_ID,
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<ActorEntity>,
    @Relation(
        parentColumn = COLUMN_NAME_MOVIE_ID,
        entityColumn = COLUMN_NAME_GENRE_ID,
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)