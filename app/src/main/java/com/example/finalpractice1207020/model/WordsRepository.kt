package com.example.finalpractice1207020.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalpractice1207020.model.roomDB.Word
import com.example.finalpractice1207020.model.roomDB.WordDao
import com.example.finalpractice1207020.services.RetrofitProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * Repository for interacting with the words DB and web service.
 */
class WordsRepository(private val wordDao: WordDao) {
    /** Firebase auth object */
    private val auth: FirebaseAuth = Firebase.auth
    /** list of words for the current user */
    var wordList: LiveData<MutableList<Word>> = wordDao.getUserWords(auth.uid ?: "")

    /**
     * Add a new word to the database.
     * @param word word to add
     */
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    /**
     * Get a random word from the words API.
     * @return a randomly-generated word
     */
    suspend fun getRandomWord(): String {
        // get some sentences from the API
        val randomSentences = RetrofitProvider.wordsService.getLipsum()
        // extract and lowercase the first word
        val word = randomSentences[0].split(" ")[0].lowercase()

        return word
    }

    /**
     * Reload the list of words for the current user.
     * Useful when the logged in user changes.
     */
    fun reloadUserWords() {
        wordList = wordDao.getUserWords(auth.uid ?: "")
    }

    /**
     * Delete all words for the current user.
     */
    suspend fun deleteUserWords() {
        wordDao.deleteUserWords(auth.uid!!)
    }

    /**
     * Delete all words in the database.
     */
    suspend fun deleteAllWords() {
        wordDao.deleteAll()
    }
}