package com.example.user.humanexpert;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 17.10.2014.
 */
public class CaseClass implements Serializable {


    private String text;
    private String imageUrl;
    private int id;
    private ArrayList<Answer> list;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Answer> getList() {
        return list;
    }

    public void setList(ArrayList<Answer> list) {
        this.list = list;
    }
}
