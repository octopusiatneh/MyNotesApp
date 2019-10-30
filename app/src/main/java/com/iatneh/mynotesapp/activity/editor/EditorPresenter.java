package com.iatneh.mynotesapp.activity.editor;
import com.iatneh.mynotesapp.database.DatabaseHelper;


public class EditorPresenter {

    DatabaseHelper db;
    private EditorView view;

    public EditorPresenter(EditorView view, DatabaseHelper db) {
        this.view = view;
        this.db = db;
    }

    void SaveNote(String title, String content, String date, int color) {
        try {
            db.addNote(title, content, date, color);
            view.onAddSuccess("Add successfully");
        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }

    void UpdateNote(int id, String title, String content, String date, int color) {
        try {
            db.updateNote(id, title, content, date, color);
            view.onAddSuccess("Update successfully");

        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }

    void DeleteNote(int id) {
        try {
            db.deleteNote(id);
            view.onAddSuccess("Delete successfully");

        } catch (Error e) {
            view.onAddError(e.getMessage());
        }
    }
}
