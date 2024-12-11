package com.example.finalpractice1207020.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room Database for words in the application.
 */
@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase: RoomDatabase() {
    // the DAO for words, will be created automatically by Room
    abstract fun wordDao(): WordDao

    companion object {
        /** instance of the Room DB, ensures it is only created once */
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        /**
         * Get or create the words Room DB if it doesn't exist.
         * @param context context to associate the database with
         * @return words Room DB
         */
        fun getDatabase(
            context: Context
        ): WordRoomDatabase {
            // run in a synchronized block so INSTANCE remains atomic and thread-safe
            return INSTANCE ?: synchronized(this) {
                // create the database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    // use destructive migration if necessary
                    .fallbackToDestructiveMigration()
                    // build database
                    .build()

                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}