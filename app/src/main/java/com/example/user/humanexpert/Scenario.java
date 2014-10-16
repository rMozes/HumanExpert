package com.example.user.humanexpert;

/**
 * Created by User on 16.10.2014.
 */
public class Scenario {
    private String id;
    private String problemTitle;
    private String caseId;

    Scenario(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
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
