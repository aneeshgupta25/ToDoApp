package com.example.todonotesapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//Dao -> data access objects
@Dao
interface NotesDao{
    @Query("SELECT * from notesData")
    fun getAll() : List<Notes>
    @Insert(onConflict = REPLACE)
    fun insert(notes : Notes)
    @Update
    fun updateNotes(notes : Notes)
    @Delete
    fun delete(notes: Notes)
}