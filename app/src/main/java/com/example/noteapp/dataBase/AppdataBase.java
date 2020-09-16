package com.example.noteapp.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database( entities = {Note.class} , version = 3,exportSchema = false)

  public abstract  class AppdataBase extends RoomDatabase {

    private  static AppdataBase mydataBase ;

    public abstract noteDao noteDao();

 public  static  final String DB_NAME = "Note_database" ;

    //public  static AppdataBase getInstance(Context context ){
     // if(mydataBase == null)
       {
        // Room.databaseBuilder(context ,AppdataBase.class,DB_NAME).fallbackToDestructiveMigration()
             //    .allowMainThreadQueries().build();

       }
   //return  mydataBase ;
    }





