package com.example.finalpractice1207020.model.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val wordTableName = "word_table"

/**
 * Data class to represent a user's word in the database.
 * Contains the word itself and the Firebase UID of the creating user.
 */
@Entity(tableName = wordTableName)
data class Word(
    @PrimaryKey @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "userID") val userID: String
)
