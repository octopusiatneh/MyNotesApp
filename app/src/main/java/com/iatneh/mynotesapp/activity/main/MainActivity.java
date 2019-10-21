package com.iatneh.mynotesapp.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iatneh.mynotesapp.R;
import com.iatneh.mynotesapp.activity.editor.EditorActivity;
import com.iatneh.mynotesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;
    FloatingActionButton fab;
    public static List<Note> noteList = new ArrayList<>();

    public static NoteAdapter adapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.add);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(this, EditorActivity.class), INTENT_ADD));

        adapter = new NoteAdapter(MainActivity.this, noteList);
        adapter.itemClickListener = ((note, position) -> {
            String title = note.getTitle();
            String notes = note.getNote();
            int color = note.getColor();
            String date = note.getDate();

            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("pos",position);
            intent.putExtra("title", title);
            intent.putExtra("note", notes);
            intent.putExtra("color", color);
            intent.putExtra("date", date);

            startActivityForResult(intent, INTENT_EDIT);
            Log.w("TAG","being edit");
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            Log.w("TAG","done edit");
            adapter.notifyDataSetChanged();
        }
    }
}
