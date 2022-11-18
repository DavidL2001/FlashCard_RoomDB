package com.example.au22_flashcard

import androidx.room.*

@Dao
interface WordDao {

    @Query("SELECT * FROM words_db")
    fun getAll():List<Word>

    @Query("SELECT * FROM words_db WHERE roll_no LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: Int): Word

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("DELETE FROM words_db")
    suspend fun deleteAll()



    // delete

    // getAllwords

}