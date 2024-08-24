package com.example.notetakingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class EditNoteActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText titleInput;
    private EditText descriptionInput;
    private long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        dbHelper = new DatabaseHelper(this);

        titleInput = findViewById(R.id.titleinput);
        descriptionInput = findViewById(R.id.descriptioninput);
        MaterialButton saveBtn = findViewById(R.id.savebtn);
        MaterialButton backbtn = findViewById(R.id.backbtn);

        // Retrieve noteId from intent
        noteId = getIntent().getLongExtra("NOTE_ID", -1);
        if (noteId != -1) {
            // Load note details and populate inputs
            Note note = dbHelper.getNoteById(noteId);
            titleInput.setText(note.getTitle());
            descriptionInput.setText(note.getDescription());
        }

        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();

            // Update note in SQLite
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TITLE, title);
            values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);

            int rowsAffected = db.update(DatabaseHelper.TABLE_NOTES, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(noteId)});

            if (rowsAffected > 0) {
                Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_SHORT).show();
                finish(); // Return to MainActivity
            } else {
                Toast.makeText(getApplicationContext(), "Error updating note", Toast.LENGTH_SHORT).show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity
                finish();
            }
        });


    }
}
