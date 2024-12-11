package com.example.finalpractice1207020.model.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    /**
     * Get all words for the specified user.
     * @param userID Firebase UID to fetch for
     * @return a LiveData representing the list of words
     */
    @Query("SELECT * FROM $wordTableName WHERE userID = :userID")
    fun getUserWords(userID: String): LiveData<MutableList<Word>>

    /**
     * Add a new word to the database.
     * @param word word to add
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    /**
     * Delete all words for the specified user.
     * @param userID Firebase UID to delete for
     */
    @Query("DELETE FROM $wordTableName WHERE userID = :userID")
    suspend fun deleteUserWords(userID: String)

    /**
     * Delete all words in the database.
     */
    @Query("DELETE FROM $wordTableName")
    suspend fun deleteAll()
}