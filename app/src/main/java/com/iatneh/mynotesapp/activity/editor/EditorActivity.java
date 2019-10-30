package com.iatneh.mynotesapp.activity.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.iatneh.mynotesapp.R;
import com.iatneh.mynotesapp.database.DatabaseHelper;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditorActivity extends AppCompatActivity implements EditorView {

    EditText et_title, et_content;
    SpectrumPalette palette;
    int color, id;

    DatabaseHelper db;

    String title, content;
    Menu actionMenu;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        db = new DatabaseHelper(this);
        presenter = new EditorPresenter(this, db);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_title = findViewById(R.id.title);
        et_content = findViewById(R.id.content);
        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(
                clr -> color = clr
        );

        //Default color of a note setup
        palette.setSelectedColor(ContextCompat.getColor(this, R.color.white));
        color = ContextCompat.getColor(this, R.color.white);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        color = intent.getIntExtra("color", 0);

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {

        if (id != -1) {
            et_title.setText(title);
            et_content.setText(content);
            palette.setSelectedColor(color);

            getSupportActionBar().setTitle("Update Note");
            readMode();
        } else {
            palette.setSelectedColor(ContextCompat.getColor(this, R.color.white));
            color = ContextCompat.getColor(this, R.color.white);
            editMode();
        }

    }

    private void editMode() {
        et_title.setFocusableInTouchMode(true);
        et_content.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        et_title.setFocusableInTouchMode(false);
        et_content.setFocusableInTouchMode(false);
        et_title.setFocusable(false);
        et_content.setFocusable(false);
        palette.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != -1) {
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = et_title.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(c);
        int color = this.color;

        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                this.finish();
                return true;

            case R.id.save:
                //Save
                if (title.isEmpty()) {
                    et_title.setError("Please enter a title");
                } else if (content.isEmpty()) {
                    et_content.setError("Please enter a note");
                } else {
                    presenter.SaveNote(title, content, date, color);
                }
                return true;
            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                //Update
                if (title.isEmpty()) {
                    et_title.setError("Please enter a title");
                } else if (content.isEmpty()) {
                    et_content.setError("Please enter a note");
                } else {
                    presenter.UpdateNote(id, title, content, date, color);
                    setResult(Activity.RESULT_OK);

                    finish();
                }
                return true;

            case R.id.delete:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm !");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setNegativeButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    presenter.DeleteNote(id);
                    setResult(Activity.RESULT_OK);

                    finish();
                });
                alertDialog.setPositiveButton("Cancel",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
