package com.example.noteapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.noteapp.Helper.RecyclerItemTouchHelper;
import com.example.noteapp.Helper.RecyclerItemTouchHelperListener;
import com.example.noteapp.R;
import com.example.noteapp.addNote.addNote;
import com.example.noteapp.dataBase.AppdataBase;
import com.example.noteapp.dataBase.Note;
import com.example.noteapp.editNote.Editnote;
import com.example.noteapp.recycler.NoteRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomePage extends AppCompatActivity implements View.OnClickListener,
        RecyclerItemTouchHelperListener {


    protected FloatingActionButton addFabButton;
    protected RecyclerView allnotesRecycler;
    protected Guideline guideline;
    protected Button resetButton;
    RecyclerView.LayoutManager layoutManager;
    public static NoteRecyclerAdapter adapter;
    public static List<Note> allnotes;
    AppdataBase myDatabase;
    Note lastNote;
    public static boolean isHaveIntent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home_page);
        initView();
        initialaizeDataBase();

        buildRecycler();

        onNoteClick();
        makeTouchHelper();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_fabButton) {
           Intent intent = new Intent(HomePage.this, addNote.class);
            startActivity(intent);

    }}

    private void initView() {
        addFabButton = (FloatingActionButton) findViewById(R.id.add_fabButton);
        addFabButton.setOnClickListener(HomePage.this);
        guideline = (Guideline) findViewById(R.id.guideline);
        allnotesRecycler = (RecyclerView) findViewById(R.id.allnotes_recycler);

    }

    public AppdataBase initialaizeDataBase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(HomePage.this, AppdataBase.class, "myDataBase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return myDatabase;

    }

    public void buildRecycler() {
        List<Note> notes = myDatabase.noteDao().getAllNotes();
        allnotes = myDatabase.noteDao().getAllNotes();
        allnotesRecycler = (RecyclerView) findViewById(R.id.allnotes_recycler);
        layoutManager = new LinearLayoutManager(HomePage.this);
        adapter = new NoteRecyclerAdapter(allnotes);
        allnotesRecycler.setLayoutManager(layoutManager);
        allnotesRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        allnotesRecycler.setHasFixedSize(true);

    }

    public void checkRecieveIntent() {


        Note note = (Note) getIntent().getSerializableExtra("new Note");
        allnotes = myDatabase.noteDao().getAllNotes();

        buildRecycler();
        isHaveIntent = false;


    }

    void onNoteClick() {
        adapter.setOnItemClick(new NoteRecyclerAdapter.onItemClick() {
            @Override
            public void Click(int position, Note note) {
                Intent intent = new Intent(HomePage.this, Editnote.class);
                intent.putExtra("this note",note);

                intent.putExtra("position", position);
                startActivity(intent);

            }
        });


    }

     void makeTouchHelper(){

        ItemTouchHelper.SimpleCallback item = new RecyclerItemTouchHelper(0,
                ItemTouchHelper.LEFT,this);
         new ItemTouchHelper(item).attachToRecyclerView(allnotesRecycler);


}


    @Override
    public void onSwipe(final RecyclerView.ViewHolder viewHolder, int direction, final int position) {
              allnotes = myDatabase.noteDao().getAllNotes();
            final Note deletedNote = allnotes.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            final int id = deletedNote.getId();
            //adapter.removeItem(deletedIndex);

            myDatabase.noteDao().deleteNote(deletedNote);
            allnotes.remove(deletedIndex);
            adapter.setNewList(myDatabase.noteDao().getAllNotes());
            adapter.notifyDataSetChanged();


             Snackbar snack=Snackbar.make(viewHolder.itemView,
                     " your note deleted",Snackbar.LENGTH_LONG).setAction("Undo ?",
                     new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  // deletedNote.setId(id);
                    adapter.restoreItem(deletedNote,deletedIndex);
                  // adapter.notifyDataSetChanged();
                   myDatabase.noteDao().addNewNote(deletedNote);

                 /*   List<Note> newNote = myDatabase.noteDao().getAllNotes();
                    adapter.setNewList(newNote);
                 */
                   /* List<Note> newNote = myDatabase.noteDao().getAllNotes();
                    adapter.setNewList(newNote);
                    adapter.notifyDataSetChanged();
*/
                }
            });
            snack.setActionTextColor(getResources().getColor(R.color.reset));
            View snackview = snack.getView();
            snackview.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            snack.show();
    }

}


