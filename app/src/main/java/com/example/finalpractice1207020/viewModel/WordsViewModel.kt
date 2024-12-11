package com.example.finalpractice1207020.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalpractice1207020.WordsApplication
import com.example.finalpractice1207020.model.WordsRepository
import com.example.finalpractice1207020.model.roomDB.Word
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for managing interaction with the repository (DB/API).
 */
class WordsViewModel(private val repository: WordsRepository): ViewModel() {
    /** list of words loaded by the repository */
    val wordList: LiveData<MutableList<Word>> = repository.wordList

    /**
     * Add a new word to the database.
     * @param word word to add
     */
    suspend fun addWord(word: Word) {
        // launch method scoped to this ViewModel's lifecycle, on a worker thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(word)
        }
    }

    /**
     * Add a random word from the words API to the list.
     */
    suspend fun addRandomWord() {
        // launch method scoped to this ViewModel's lifecycle, on a worker thread
        viewModelScope.launch(Dispatchers.IO) {
            // get a random word from the API
            val randomWord = repository.getRandomWord()

            // add it to the current user's list
            repository.insert(Word(randomWord, Firebase.auth.uid!!))
        }
    }

    /**
     * Delete all words for the current user.
     */
    suspend fun deleteUserWords() {
        // launch method scoped to this ViewModel's lifecycle, on a worker thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserWords()
        }
    }

    /**
     * Delete all words in the database.
     */
    suspend fun deleteAllWords() {
        // launch method scoped to this ViewModel's lifecycle, on a worker thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllWords()
        }
    }

    companion object {
        /**
         * Factory for this ViewModel to allow passing in params (the modern way).
         * https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // get the repository from the custom Application class
                val repository = (this[APPLICATION_KEY] as WordsApplication).repository
                // create an instance of the ViewModel using the repository
                WordsViewModel(repository)
            }
        }
    }
}