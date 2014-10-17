package com.example.user.humanexpert;

/**
 * Created by User on 17.10.2014.
 */
public class Answer {
    private String newText;
    private String newId;
    private String newCaseId;

    Answer(){}
    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewCaseId() {
        return newCaseId;
    }

    public void setNewCaseId(String newCaseId) {
        this.newCaseId = newCaseId;
    }
}
