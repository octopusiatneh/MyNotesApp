package com.iatneh.mynotesapp.activity.editor;

import android.util.Log;

import com.iatneh.mynotesapp.activity.main.MainActivity;
import com.iatneh.mynotesapp.model.Note;

import static com.iatneh.mynotesapp.activity.main.MainActivity.adapter;
import static com.iatneh.mynotesapp.activity.main.MainActivity.noteList;

public class EditorPresenter {

    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    void SaveNote(Note note) {
        try {
            noteList.add(note);
            view.onAddSuccess("Add successfully");
            for(Note note1 : noteList)
            {
                Log.e("ADD",note1.toString());
            }
        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }

    void UpdateNote(Note note, int position) {
        try {
            noteList.set(position,note);
            adapter.notifyItemChanged(position);
            adapter.notifyDataSetChanged();
            view.onAddSuccess("Update successfully");
            for(Note note2 : noteList)
            {
                Log.e("UPDATE",note2.toString());
            }

        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }

    void DeleteNote(int position) {
        try {
            noteList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyDataSetChanged();
            view.onAddSuccess("Delete successfully");
        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }
}
