/*
package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button cancel, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        cancel = findViewById(R.id.buttonCancel);
        save = findViewById(R.id.buttonSave);

        cancel.setOnClickListener((View v) -> {
            Toast.makeText(getApplicationContext(), "Nothing saved", Toast.LENGTH_LONG).show();
            finish();
        });

        save.setOnClickListener((View v) -> {
            saveNote();
        });
    }

    public void saveNote()
    {
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();

        Intent i = new Intent();
        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDescription", noteDescription);
        setResult(RESULT_OK, i);
        finish();
        Toast.makeText(getApplicationContext(), "Added new note", Toast.LENGTH_LONG).show();
    }
}*/

package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Spinner categorySpinner; // Eklenen Spinner
    Button cancel, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        cancel = findViewById(R.id.buttonCancel);
        save = findViewById(R.id.buttonSave);

        // Spinner'ı bağla ve kategori seçeneklerini ayarla
        categorySpinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        cancel.setOnClickListener((View v) -> {
            Toast.makeText(getApplicationContext(), "Nothing saved", Toast.LENGTH_LONG).show();
            finish();
        });

        save.setOnClickListener((View v) -> {
            saveNote();
        });
    }

    public void saveNote() {
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();
        String selectedCategory = categorySpinner.getSelectedItem().toString(); // Seçilen kategori

        Intent i = new Intent();
        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDescription", noteDescription);
        i.putExtra("noteCategory", selectedCategory); // Kategori bilgisini ekledik
        setResult(RESULT_OK, i);
        finish();
        Toast.makeText(getApplicationContext(), "Added new note", Toast.LENGTH_LONG).show();
    }
}

