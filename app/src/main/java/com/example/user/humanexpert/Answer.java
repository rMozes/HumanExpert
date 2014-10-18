package com.example.user.humanexpert;

/**
 * Created by User on 17.10.2014.
 */
public class Answer {
    private String newText;
    private int newId;
    private int newCaseId;

    Answer() {
    }

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public int getNewCaseId() {
        return newCaseId;
    }

    public void setNewCaseId(int newCaseId) {
        this.newCaseId = newCaseId;
    }
}
