package com.example.noteapp.recycler;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.dataBase.Note;

import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

   List <Note> notes ;
   View view ;
   onItemClick onItemClick;

    public void setOnItemClick(NoteRecyclerAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public NoteRecyclerAdapter(List<Note> notes)
    {
        this.notes = notes;
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.everyitem_inrecycler,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Note note = notes.get(position);
        holder.NoteTitle.setText(note.getTitle());
        holder.NoteDate.setText(note.getDate());
        holder.NoteDescription.setText(note.getContent());

        if(onItemClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.Click(position,note);
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        return notes.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView NoteTitle ;
         TextView NoteDate ;
         TextView NoteDescription ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NoteTitle = itemView.findViewById(R.id.note_title);
            NoteDate = itemView.findViewById(R.id.note_date);
            NoteDescription = itemView.findViewById(R.id.note_description);
        }
    }

     public void setNewList(List<Note> newNotes){
        this.notes= newNotes;
       }

    public interface onItemClick{

       void  Click(int position , Note note);

    }


    public  void removeItem(int position){
        notes.remove(position);
        notifyItemRemoved(position);

    }
  public  void  restoreItem(Note note, int position){

        notes.add(position,note);
         notifyItemInserted(position);
  }

}
