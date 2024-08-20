package com.example.notetakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set up the add note button
        MaterialButton addNoteBtn = findViewById(R.id.addnewnotebtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with data from SQLite
        List<Note> notesList = dbHelper.getAllNotes();
        myAdapter = new MyAdapter(this, notesList);
        recyclerView.setAdapter(myAdapter);

        // Optional: Implement a way to refresh the list if needed
        // For example, if you're adding or deleting notes in AddNoteActivity
        // you might need to refresh the list here manually
    }

    // Call this method to refresh the notes list after adding or deleting notes
    public void refreshNotesList() {
        List<Note> updatedNotesList = dbHelper.getAllNotes();
        myAdapter.updateNotes(updatedNotesList);
    }
}
