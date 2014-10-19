package com.example.user.humanexpert;

import java.util.ArrayList;

/**
 * Created by User on 17.10.2014.
 */
public class CaseClass {
    private int id;
    private String text;
    private String imageUrl;
    private ArrayList<Answer> list;


    CaseClass(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Answer> getList() {
        return list;
    }

    public void setList(ArrayList<Answer> list) {
        this.list = list;
    }
}
