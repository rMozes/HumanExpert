package com.example.user.humanexpert;

/**
 * Created by User on 16.10.2014.
 */
public class Scenario {
    private int id;
    private String problemTitle;
    private int caseId;

    Scenario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    @Override
    public String toString() {
        return problemTitle;
    }
}
