package com.example.androidtodolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diary {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;
    public String datetime;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getDatetime(){
        return datetime;
    }

    public Diary(){
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }
}



