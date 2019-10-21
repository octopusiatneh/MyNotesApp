package com.iatneh.mynotesapp.model;

import java.util.Date;

public class Note {
    public String Title;
    public String Note;
    public int Color;
    public String Date;

    public Note(String title, String note, int color, String date) {
        Title = title;
        Note = note;
        Color = color;
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Note{" +
                "Title='" + Title + '\'' +
                ", Note='" + Note + '\'' +
                ", Color=" + Color +
                ", Date='" + Date + '\'' +
                '}';
    }
}
