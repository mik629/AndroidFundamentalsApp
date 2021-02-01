package com.github.mik629.android.fundamentals.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.COLUMN_NAME_RATING
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.MOVIES_TABLE_NAME
import com.github.mik629.android.fundamentals.data.db.models.*

@Dao
interface MovieDao {
    @Transaction
    @Query(value = "SELECT * FROM $MOVIES_TABLE_NAME ORDER BY $COLUMN_NAME_RATING DESC")
    suspend fun getAllMovies(): List<MovieWithActorsAndGenres>

    @Insert
    suspend fun insertMovies(movies: List<MovieDbEntity>)

    @Insert
    suspend fun insertActors(actors: List<ActorDbEntity>)

    @Insert
    suspend fun insertMovieActors(movieActors: List<MovieActorCrossRef>)

    @Insert
    suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Insert
    suspend fun insertMovieGenres(movieGenres: List<MovieGenreCrossRef>)
}
