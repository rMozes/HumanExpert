package com.example.user.humanexpert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19.10.2014.
 */
public class DBController {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    DBController(Context context){
        this.context = context;
    }
    public long insertScen(Scenario scenario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_SCEN_ID, scenario.getId());
        values.put(dbHelper.KEY_SCEN_TEXT, scenario.getProblemTitle());
        values.put(dbHelper.KEY_SCEN_CASEID, scenario.getCaseId());

        long row_id = db.insert(dbHelper.TABLE_SCEN, null, values);
        return row_id;
    }

    public long insertCase(CaseClass caseClass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_CASE_ID, caseClass.getId());
        values.put(dbHelper.KEY_CASE_TEXT, caseClass.getText());
        values.put(dbHelper.KEY_CASE_IMAGE, caseClass.getImageUrl());

        long row_id = db.insert(dbHelper.TABLE_CASE, null, values);
        return row_id;
    }

    public long insertAnswer(Answer answer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_ANSWER_ID, answer.getNewId());
        values.put(dbHelper.KEY_ANSWER_TEXT, answer.getNewText());
        values.put(dbHelper.KEY_ANSWER_CASEID, answer.getNewCaseId());

        long row_id = db.insert(dbHelper.TABLE_ANSWER, null, values);
        return row_id;
    }

    public Scenario getScenario(long scen_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_SCEN + " WHERE " + dbHelper.KEY_SCEN_ID + " = " + scen_id;
        Log.e(dbHelper.LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Scenario scenario = new Scenario();
        scenario.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_SCEN_ID)));
        scenario.setProblemTitle((c.getString(c.getColumnIndex(dbHelper.KEY_SCEN_TEXT))));
        scenario.setCaseId(c.getInt(c.getColumnIndex(dbHelper.KEY_SCEN_CASEID)));
        return scenario;
    }

    public CaseClass getCaseClass(long case_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_CASE + " WHERE " + dbHelper.KEY_CASE_ID + " = " + case_id;
        Log.e(dbHelper.LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        CaseClass caseClass = new CaseClass();
        caseClass.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_CASE_ID)));
        caseClass.setText((c.getString(c.getColumnIndex(dbHelper.KEY_CASE_TEXT))));
        caseClass.setImageUrl(c.getString(c.getColumnIndex(dbHelper.KEY_CASE_IMAGE)));
        return caseClass;
    }

    public Answer getAnswer(long answer_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_ANSWER + " WHERE " + dbHelper.KEY_ANSWER_ID + " = " + answer_id;
        Log.e(dbHelper.LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Answer answer = new Answer();
        answer.setNewId(c.getInt(c.getColumnIndex(dbHelper.KEY_ANSWER_ID)));
        answer.setNewText((c.getString(c.getColumnIndex(dbHelper.KEY_ANSWER_TEXT))));
        answer.setNewCaseId(c.getInt(c.getColumnIndex(dbHelper.KEY_ANSWER_CASEID)));
        return answer;
    }

    public List<Scenario> getAllScenarios() {
        List<Scenario> scenariosList = new ArrayList<Scenario>();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_SCEN;
        Log.e(dbHelper.LOG, selectQuery);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Scenario scenario = new Scenario();
                scenario.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_SCEN_ID)));
                scenario.setProblemTitle((c.getString(c.getColumnIndex(dbHelper.KEY_SCEN_TEXT))));
                scenario.setCaseId(c.getInt(c.getColumnIndex(dbHelper.KEY_SCEN_CASEID)));
                scenariosList.add(scenario);
            } while (c.moveToNext());
        }
        return scenariosList;
    }

    public List<CaseClass> getAllCases() {
        List<CaseClass> casesList = new ArrayList<CaseClass>();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_CASE;
        Log.e(dbHelper.LOG, selectQuery);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                CaseClass caseClass = new CaseClass();
                caseClass.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_CASE_ID)));
                caseClass.setText((c.getString(c.getColumnIndex(dbHelper.KEY_CASE_TEXT))));
                caseClass.setImageUrl(c.getString(c.getColumnIndex(dbHelper.KEY_CASE_IMAGE)));
                casesList.add(caseClass);
            } while (c.moveToNext());
        }
        return casesList;
    }

    public List<Answer> getAnswers() {
        List<Answer> answersList = new ArrayList<Answer>();
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_ANSWER;
        Log.e(dbHelper.LOG, selectQuery);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setNewId(c.getInt(c.getColumnIndex(dbHelper.KEY_ANSWER_ID)));
                answer.setNewText((c.getString(c.getColumnIndex(dbHelper.KEY_ANSWER_TEXT))));
                answer.setNewCaseId(c.getInt(c.getColumnIndex(dbHelper.KEY_ANSWER_CASEID)));
                answersList.add(answer);
            } while (c.moveToNext());
        }
        return answersList;
    }
    public DBController open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}
