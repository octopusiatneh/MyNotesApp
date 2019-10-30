package com.iatneh.mynotesapp.activity.main;

import static com.iatneh.mynotesapp.activity.main.MainActivity.noteList;

public class MainPresenter {
    MainView view;

    public MainPresenter(MainView view)
    {
        this.view = view;
    }

    public void deleteNote(int position) {
        try {
            noteList.remove(position);
            view.onDeleteSuccessfully("Delete Successfully");
        }
        catch (Error e)
        {
            view.onDeleteFailure(e.toString());
        }
    }
}
