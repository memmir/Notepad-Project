package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    FloatingActionButton developer;

    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //adapter = new NoteAdapter();

        // RecyclerView'a adapter'ı set et
        //recyclerView.setAdapter(adapter);

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);


        // RecyclerView'a adapter'ı set et


        developer = findViewById(R.id.floatingActionButtonDeveloper);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1); // 1, sütun sayısını temsil eder
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter();
        //NoteAdapter adapter = new NoteAdapter();

        recyclerView.setAdapter(adapter);

        developer.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, DevelopersActivity.class);
            startActivity(intent);
        });

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, (List<Note> notes) -> {
            // update Recycler View
            adapter.setNotes(notes);
        });

        new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Deleted note", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener((Note note) -> {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("description", note.getDescription());
            startActivityForResult(intent, 2);
        });

        EditText editTextSearch = findViewById(R.id.editTextSearch);

        // Arama çubuğuna metin girişi yapıldığında notları filtrele
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterNotes(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    private void filterNotes(String searchText) {

        if(adapter != null){
            List<Note> filteredNotes = new ArrayList<>();


            if (searchText.isEmpty()) {
                // Eğer arama çubuğu boşsa, bütün notları göster
                filteredNotes.addAll(noteViewModel.getAllNotes().getValue());
            } else {
                // Arama çubuğuna metin girişi yapıldığında notları filtrele
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    Note note = adapter.getNotes(i);
                    if (note.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                            note.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                        filteredNotes.add(note);
                    }
                }
            }

            adapter.setNotes(filteredNotes);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.new_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra("noteTitle");
            String description = data.getStringExtra("noteDescription");

            Note note = new Note(title, description);
            noteViewModel.insert(note);
        }
        else if (requestCode == 2 && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("noteId", -1);
            String title = data.getStringExtra("titleLast");
            String description = data.getStringExtra("descriptionLast");

            Note note = new Note(title, description);
            note.setId(id);
            noteViewModel.update(note);
        }
    }
}