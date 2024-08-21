package com.example.notetakingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        MaterialButton saveBtn = findViewById(R.id.savebtn);

        dbHelper = new DatabaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createdTime = System.currentTimeMillis();

                // Insert note into the SQLite database
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
                values.put(DatabaseHelper.COLUMN_CREATED_TIME, createdTime);

                long newRowId = db.insert(DatabaseHelper.TABLE_NOTES, null, values);

                if (newRowId != -1) {
                    Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                    // Set result to indicate a new note was added
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error saving note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
