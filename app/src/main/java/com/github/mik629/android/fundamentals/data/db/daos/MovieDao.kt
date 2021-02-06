package com.github.mik629.android.fundamentals.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.github.mik629.android.fundamentals.data.db.models.*
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_RATING
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.MOVIES_TABLE_NAME

@Dao
interface MovieDao {
    @Transaction
    @Query(value = "SELECT * FROM $MOVIES_TABLE_NAME ORDER BY $COLUMN_NAME_RATING DESC")
    suspend fun getAllMovies(): List<MovieWithActorsAndGenres>

    @Transaction
    @Insert
    suspend fun insertMovies(movies: List<MovieDbEntity>)

    @Transaction
    @Insert
    suspend fun insertActors(actors: List<ActorDbEntity>)

    @Transaction
    @Insert
    suspend fun insertMovieActors(movieActors: List<MovieActorCrossRef>)

    @Transaction
    @Insert
    suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Transaction
    @Insert
    suspend fun insertMovieGenres(movieGenres: List<MovieGenreCrossRef>)

    @Transaction
    suspend fun insertData(
        movies: List<MovieDbEntity>,
        actors: List<ActorDbEntity>,
        movieActors: List<MovieActorCrossRef>,
        genres: List<GenreDbEntity>,
        movieGenres: List<MovieGenreCrossRef>
    ) {
        insertMovies(movies)
        insertActors(actors)
        insertMovieActors(movieActors)
        insertGenres(genres)
        insertMovieGenres(movieGenres)
    }
}
