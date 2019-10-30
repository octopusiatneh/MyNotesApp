package com.iatneh.mynotesapp.activity.main;

public interface MainView {
    void onDeleteSuccessfully(String message);
    void onDeleteFailure(String message);
    void initView();
}
