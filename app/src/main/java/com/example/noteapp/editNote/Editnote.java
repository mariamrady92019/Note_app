package com.example.noteapp.editNote;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.noteapp.Home.HomePage;
import com.example.noteapp.R;
import com.example.noteapp.dataBase.AppdataBase;
import com.example.noteapp.dataBase.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class Editnote extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    protected FloatingActionButton saveEdit;
    protected EditText title;
    protected EditText content;
    protected Button date;
    protected FloatingActionButton backButton;
    String titleS;
    String contentS;
    String dateS;
    int position;
    List<Note> allNotes;
    AppdataBase myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_editnote);
        initView();
        initialaizeDataBase();
        recieve();
        justStarted();
    }


    public void recieve() {

        Note note = (Note) getIntent().getSerializableExtra("this note");
        position = getIntent().getIntExtra("position", 0);
        titleS = note.getTitle();
        contentS = note.getContent();
        dateS = note.getDate();


    }


    private void initView() {
        saveEdit = (FloatingActionButton) findViewById(R.id.save_Edit);
        saveEdit.setOnClickListener(Editnote.this);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        date = (Button) findViewById(R.id.date);
        date.setOnClickListener(Editnote.this);
        backButton = (FloatingActionButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(Editnote.this);
    }

    @Override
    public void onClick(View view) {
        allNotes = myDatabase.noteDao().getAllNotes();
        if (view.getId() == R.id.save_Edit) {

            String EditedTitle = title.getText().toString();
            String EditedContent = content.getText().toString();
            String Editeddate = date.getText().toString();

            int id = allNotes.get(position).getId();
            Note EditedNote = new Note(EditedTitle, EditedContent, Editeddate);
            EditedNote.setId(id);

            /**
             allNotes.remove(position);
             allNotes.set(position,EditedNote); **/

            myDatabase.noteDao().update(EditedNote);
            notifyChanges();
            custmToast("Editted successfully");
            finish();

        } else if (view.getId() == R.id.back_button) {

            onBackPressed();
        } else if (view.getId() == R.id.date) {
            showDatePickerDialogs();
        }
    }

    public void showDatePickerDialogs() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    public AppdataBase initialaizeDataBase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(Editnote.this, AppdataBase.class, "myDataBase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return myDatabase;

    }

    void justStarted() {
        title.setText(titleS);
        content.setText(contentS);
        date.setText(dateS);

    }

    public void notifyChanges() {
        List<Note> last = myDatabase.noteDao().getAllNotes();
        HomePage.adapter.setNewList(last);
        HomePage.adapter.notifyDataSetChanged();

    }

    void custmToast(String message) {

        Toast toast = Toast.makeText(Editnote.this, message, Toast.LENGTH_SHORT);
        View view = toast.getView();


//Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.whitet));

        toast.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String dateFormat = dayOfMonth + "/" + month + "/" + year;
        date.setText(dateFormat);

    }
}
