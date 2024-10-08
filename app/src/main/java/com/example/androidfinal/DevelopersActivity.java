package com.example.androidfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DevelopersActivity extends AppCompatActivity {

    ListView listViewDevelopers;
    String[] developers;
    ArrayAdapter<String> adapter;
    FloatingActionButton buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Developer");
        setContentView(R.layout.activity_developers);

        listViewDevelopers = findViewById(R.id.listViewDeveloper);
        buttonClose = findViewById(R.id.floatingActionButtonClose);

        developers = getResources().getStringArray(R.array.developer);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, developers);
        listViewDevelopers.setAdapter(adapter);

        buttonClose.setOnClickListener((View v) -> {
            finish();
        });
    }
}