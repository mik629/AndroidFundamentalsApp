package com.github.mik629.android.fundamentals.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieActorCrossRef
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_MOVIE_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_RATING
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.MOVIES_TABLE_NAME
import com.github.mik629.android.fundamentals.data.db.models.MovieGenreCrossRef
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres

@Dao
abstract class MovieDao {
    @Transaction
    @Query("SELECT * FROM $MOVIES_TABLE_NAME ORDER BY $COLUMN_RATING DESC")
    abstract suspend fun getAllMovies(): List<MovieWithActorsAndGenres>

    @Query("SELECT * FROM $MOVIES_TABLE_NAME WHERE $COLUMN_MOVIE_ID = :id")
    abstract suspend fun getMovie(id: Long): MovieWithActorsAndGenres?

    @Transaction
    open suspend fun insertData(
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

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertMovies(movies: List<MovieDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertActors(actors: List<ActorDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertMovieActors(movieActors: List<MovieActorCrossRef>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertGenres(genres: List<GenreDbEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertMovieGenres(movieGenres: List<MovieGenreCrossRef>)
}
