package com.example.notetakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context context;
    private List<Note> notesList;
    private final DatabaseHelper dbHelper;

    public MyAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.dbHelper = new DatabaseHelper(context); // Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());

        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formattedTime);

        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu menu = new PopupMenu(context, v);
            menu.getMenu().add("DELETE");
            menu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("DELETE")) {
                    // Delete the note from SQLite
                    dbHelper.deleteNote(note.getId());
                    notesList.remove(position); // Remove from the list
                    notifyItemRemoved(position); // Notify adapter of the change
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            menu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void updateNotes(List<Note> newNotesList) {
        this.notesList = newNotesList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}
