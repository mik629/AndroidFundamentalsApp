package com.github.mik629.android.fundamentals.data.db.daos

import androidx.room.*
import com.github.mik629.android.fundamentals.data.db.models.*
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_RATING
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.MOVIES_TABLE_NAME

@Dao
interface MovieDao {
    @Transaction
    @Query(value = "SELECT * FROM $MOVIES_TABLE_NAME ORDER BY $COLUMN_NAME_RATING DESC")
    suspend fun getAllMovies(): List<MovieWithActorsAndGenres>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActors(movieActors: List<MovieActorCrossRef>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
