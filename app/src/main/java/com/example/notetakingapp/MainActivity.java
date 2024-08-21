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

    private static final int REQUEST_CODE_ADD_NOTE = 1;

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
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with data from SQLite
        List<Note> notesList = dbHelper.getAllNotes();
        myAdapter = new MyAdapter(this, notesList);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            refreshNotesList();
        }
    }

    // Call this method to refresh the notes list after adding or deleting notes
    private void refreshNotesList() {
        List<Note> updatedNotesList = dbHelper.getAllNotes();
        myAdapter.updateNotes(updatedNotesList);
    }
}
