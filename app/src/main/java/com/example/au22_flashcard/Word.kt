package com.example.au22_flashcard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words_db")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "english") var english: String?,
    @ColumnInfo(name = "swedish") var swedish: String?,
    @ColumnInfo(name = "roll_no") var rollNo: Int?,
)

