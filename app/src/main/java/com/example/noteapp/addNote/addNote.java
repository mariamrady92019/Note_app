package com.example.noteapp.addNote;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.noteapp.Base.BaseActivity;
import com.example.noteapp.Home.HomePage;
import com.example.noteapp.R;
import com.example.noteapp.dataBase.AppdataBase;
import com.example.noteapp.dataBase.Note;
import com.example.noteapp.recycler.NoteRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class addNote extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    RecyclerView recyclerView;
    NoteRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Note LastNode;
    List<Note> allnotes = new ArrayList<>();
    AppdataBase myDatabase;
    protected EditText title;
    protected EditText content;
    protected Button date;
    protected Button addNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_note);
        initView();
        initilaizDataBase();

    }

    //@Override
    // public void onBackPressed() {
    //  super.onBackPressed();
    // HomePage.isHaveIntent = true ;
    // Intent intent = new Intent(addNote.this , HomePage.class);
    // intent.putExtra("new Note", LastNode );
    // startActivity(intent);
    //بس مشكله دي ان هيكون عندي اول باك اللي راجع من الاكتفيتي
    // ال شييوبعد كده باك تاني لاكتفتي المين نفسها ومفيهاش تغيير

    //}**/

    boolean checkValidate() {
        boolean validate = true;
        if (title.getText().toString().trim().isEmpty() ||
                content.getText().toString().trim().isEmpty() ||
                date.getText().toString().trim().isEmpty()) {
            validate = false;
        }

        return validate;

    }


    @Override
    public void onClick(View view) {
        String titleS = title.getText().toString();
        String contentS = content.getText().toString();
        String dateS = date.getText().toString();

        if (view.getId() == R.id.addNewNote) {
            if (checkValidate() == true) {
                Note note = new Note(titleS, contentS, dateS);
                LastNode = note;
                myDatabase.noteDao().addNewNote(note);

                notifyChanges();
                showMessage("note added successfully", "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }, false);

            } else {
                custmToast("please complete note");
            }

        } else if (view.getId() == R.id.date) {
            showDatePickerDialogs();
        }

    }

    private void initView() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        date = (Button) findViewById(R.id.date);
        date.setOnClickListener(addNote.this);
        addNewNote = (Button) findViewById(R.id.addNewNote);
        addNewNote.setOnClickListener(addNote.this);

    }

    public AppdataBase initilaizDataBase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(addNote.this, AppdataBase.class, "myDataBase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return myDatabase;
    }

    public void notifyChanges() {
        List<Note> last = myDatabase.noteDao().getAllNotes();
        HomePage.adapter.setNewList(last);
        HomePage.adapter.notifyDataSetChanged();

    }

    public void custmToast(String message) {

        Toast toast = Toast.makeText(addNote.this, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.whitet));

        toast.show();
    }

    public void showDatePickerDialogs() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String dateFormat = dayOfMonth + "/" + month + "/" + year;
        date.setText(dateFormat);

    }
}
