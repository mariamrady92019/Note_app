package com.example.noteapp.dataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {

@PrimaryKey(autoGenerate = true )
  int id  ;

  String title ;
  String content ;
  String date;

  @Ignore
    public Note(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
  }

    public Note() {
      //intialazation
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
