package com.github.mik629.android.fundamentals.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.*

@Database(
    entities = [
        MovieDbEntity::class, ActorDbEntity::class, GenreDbEntity::class, MovieActorCrossRef::class, MovieGenreCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDb : RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        private const val MOVIES_DB_NAME = "Movies.db"

        fun createDb(context: Context) =
            Room.databaseBuilder(context, MovieDb::class.java, MOVIES_DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}