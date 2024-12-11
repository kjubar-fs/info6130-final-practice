package com.example.finalpractice1207020

import android.app.Application
import com.example.finalpractice1207020.model.WordsRepository
import com.example.finalpractice1207020.model.roomDB.WordRoomDatabase

/**
 * Custom Application class to provide global access to the repository.
 * Also ensures only one instance of the repository is created.
 */
class WordsApplication: Application() {
    /** words Room DB */
    private val database by lazy { WordRoomDatabase.getDatabase(this) }
    /** words repository */
    val repository by lazy { WordsRepository(database.wordDao()) }
}