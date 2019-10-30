package com.iatneh.mynotesapp.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iatneh.mynotesapp.R;
import com.iatneh.mynotesapp.activity.editor.EditorActivity;
import com.iatneh.mynotesapp.database.DatabaseHelper;
import com.iatneh.mynotesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, SearchView.OnQueryTextListener {

    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;

    SearchView searchView;

    FloatingActionButton fab;
    public static List<Note> noteList = new ArrayList<>();

    public static NoteAdapter adapter;

    MainPresenter presenter;

    RecyclerView recyclerView;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        presenter = new MainPresenter(this);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        fab = findViewById(R.id.add);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(this, EditorActivity.class), INTENT_ADD));

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode-resultCode",String.valueOf(requestCode));
        Log.d("requestCode-resultCode", String.valueOf(resultCode));
        // updateView();
        if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            initView();

        }
        if (requestCode == INTENT_ADD && resultCode == RESULT_OK) {
            initView();
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            presenter.deleteNote(position);
        }
    };

    @Override
    public void onDeleteSuccessfully(String message) {
        Toast.makeText(MainActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFailure(String message) {
        Toast.makeText(MainActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initView() {

        noteList = db.getAllNote();
        adapter = new NoteAdapter(this, noteList);
        recyclerView.setAdapter(adapter);
        adapter.itemClickListener = ((note, position) -> {
            String title = note.getTitle();
            String content = note.getContent();
            int color = note.getColor();
            int id = note.getId();
            String date = note.getDate();

            Log.d("Id",String.valueOf(id));

            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("color", color);
            intent.putExtra("date", date);

            startActivityForResult(intent, INTENT_EDIT);
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
