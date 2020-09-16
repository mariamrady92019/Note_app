package com.example.noteapp.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface noteDao {

    @Insert
   void addNewNote(Note note) ;

    @Delete
    void deleteNote(Note note );


   @Query(" select * from  Note")
    List<Note> getAllNotes();

   @Update
    void  update(Note note);

    @Query("DELETE FROM Note")
    public void dropTable();




}
