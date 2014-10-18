package com.example.user.humanexpert;

import java.util.ArrayList;

/**
 * Created by User on 17.10.2014.
 */
public class Case {
    private String id;
    private String textQuestion;
    private String imageUrl;
    private ArrayList<Answer> list;


    Case(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
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
