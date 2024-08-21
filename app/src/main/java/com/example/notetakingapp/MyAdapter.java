package com.example.notetakingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());

        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formattedTime);

        holder.overflowMenu.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(context, v);
            menu.getMenuInflater().inflate(R.menu.note_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    // Handle edit action
                    Intent intent = new Intent(context, EditNoteActivity.class);
                    intent.putExtra("NOTE_ID", note.getId());
                    context.startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    // Handle delete action
                    dbHelper.deleteNote(note.getId());
                    notesList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });
            menu.show();
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
        ImageView overflowMenu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
            overflowMenu = itemView.findViewById(R.id.overflow_menu);
        }
    }
}
