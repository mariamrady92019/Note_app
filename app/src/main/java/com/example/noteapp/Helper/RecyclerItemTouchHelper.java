package com.example.noteapp.Helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

     private  RecyclerItemTouchHelperListener  listener ;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs ,RecyclerItemTouchHelperListener  helperListener ) {
        super(dragDirs, swipeDirs);
        this.listener=helperListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener!=null){
         listener.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());

        }
    }
}
