package com.iatneh.mynotesapp.model;

import java.io.Serializable;

public class Note implements Comparable<Note>, Serializable {
    public int Id;
    public String Title;
    public String Content;
    public int Color;
    public String Date;

    public Note()
    {

    }

    public Note(String title, String content, String date, int color) {
        Title = title;
        Content = content;
        Date = date;
        Color = color;
    }

    public Note(int id, String title, String content, String date, int color) {
        Id = id;
        Title = title;
        Content = content;
        Date = date;
        Color = color;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
                ", Content='" + Content + '\'' +
                ", Color=" + Color +
                ", Date='" + Date + '\'' +
                '}';
    }

    @Override
    public int compareTo(Note o) {
        return Title.compareToIgnoreCase(o.getTitle());
    }
}
