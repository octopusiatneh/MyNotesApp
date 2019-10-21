package com.iatneh.mynotesapp.activity.main;

import com.iatneh.mynotesapp.model.Note;

import java.util.List;

public interface MainView {
    void onGetResult(List<Note> notes);
    void onErrorLoading(String message);
}
