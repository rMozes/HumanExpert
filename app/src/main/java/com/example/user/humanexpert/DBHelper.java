package com.example.user.humanexpert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.10.2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG = "DBHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "humanExpert";

    private static final String TABLE_SCEN = "scenarios";
    private static final String TABLE_CASE = "cases";
    private static final String TABLE_ANSWER = "answers";

    private static final String KEY_SCEN_ID = "id";
    private static final String KEY_SCEN_TEXT = "text";
    private static final String KEY_SCEN_CASEID = "caseId";

    private static final String KEY_CASE_ID = "id";
    private static final String KEY_CASE_TEXT = "text";
    private static final String KEY_CASE_IMAGE = "image";

    private static final String KEY_ANSWER_ID = "id";
    private static final String KEY_ANSWER_TEXT = "text";
    private static final String KEY_ANSWER_CASEID = "caseId";

    private static final String CREATE_TABLE_SCEN = "CREATE TABLE "
            + TABLE_SCEN + "(" + KEY_SCEN_ID + " INTEGER PRIMARY KEY," + KEY_SCEN_TEXT
            + " TEXT," + KEY_SCEN_CASEID + " INTEGER " + ")";

    private static final String CREATE_TABLE_CASE = "CREATE TABLE " + TABLE_CASE
            + "(" + KEY_CASE_ID + " INTEGER PRIMARY KEY," + KEY_CASE_TEXT + " TEXT,"
            + KEY_CASE_IMAGE + " TEXT " + ")";

    private static final String CREATE_TABLE_ANSWER = "CREATE TABLE "
            + TABLE_ANSWER + "(" + KEY_ANSWER_ID + " INTEGER PRIMARY KEY,"
            + KEY_ANSWER_TEXT + " TEXT," + KEY_ANSWER_CASEID + " INTEGER " + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCEN);
        db.execSQL(CREATE_TABLE_CASE);
        db.execSQL(CREATE_TABLE_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        onCreate(db);
    }

    public long createScen(Scenario scenario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SCEN_ID, scenario.getId());
        values.put(KEY_SCEN_TEXT, scenario.getProblemTitle());
        values.put(KEY_SCEN_CASEID, scenario.getCaseId());

        long row_id = db.insert(TABLE_SCEN, null, values);
        return row_id;
    }

    public long createCase(CaseClass caseClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CASE_ID, caseClass.getId());
        values.put(KEY_CASE_TEXT, caseClass.getText());
        values.put(KEY_CASE_IMAGE, caseClass.getImageUrl());

        long row_id = db.insert(TABLE_CASE, null, values);
        return row_id;
    }

    public long createAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ANSWER_ID, answer.getNewId());
        values.put(KEY_ANSWER_TEXT, answer.getNewText());
        values.put(KEY_ANSWER_CASEID, answer.getNewCaseId());

        long row_id = db.insert(TABLE_ANSWER, null, values);
        return row_id;
    }

    public Scenario getScenario(long scen_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SCEN + " WHERE " + KEY_SCEN_ID + " = " + scen_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Scenario scenario = new Scenario();
        scenario.setId(c.getInt(c.getColumnIndex(KEY_SCEN_ID)));
        scenario.setProblemTitle((c.getString(c.getColumnIndex(KEY_SCEN_TEXT))));
        scenario.setCaseId(c.getInt(c.getColumnIndex(KEY_SCEN_CASEID)));
        return scenario;
    }

    public CaseClass getCaseClass(long case_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CASE + " WHERE " + KEY_CASE_ID + " = " + case_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        CaseClass caseClass = new CaseClass();
        caseClass.setId(c.getInt(c.getColumnIndex(KEY_CASE_ID)));
        caseClass.setText((c.getString(c.getColumnIndex(KEY_CASE_TEXT))));
        caseClass.setImageUrl(c.getString(c.getColumnIndex(KEY_CASE_IMAGE)));
        return caseClass;
    }

    public Answer getAnswer(long answer_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWER + " WHERE " + KEY_ANSWER_ID + " = " + answer_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Answer answer = new Answer();
        answer.setNewId(c.getInt(c.getColumnIndex(KEY_ANSWER_ID)));
        answer.setNewText((c.getString(c.getColumnIndex(KEY_ANSWER_TEXT))));
        answer.setNewCaseId(c.getInt(c.getColumnIndex(KEY_ANSWER_CASEID)));
        return answer;
    }

    public List<Scenario> getAllScenarios() {
        List<Scenario> scenariosList = new ArrayList<Scenario>();
        String selectQuery = "SELECT  * FROM " + TABLE_SCEN;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Scenario scenario = new Scenario();
                scenario.setId(c.getInt(c.getColumnIndex(KEY_SCEN_ID)));
                scenario.setProblemTitle((c.getString(c.getColumnIndex(KEY_SCEN_TEXT))));
                scenario.setCaseId(c.getInt(c.getColumnIndex(KEY_SCEN_CASEID)));
                scenariosList.add(scenario);
            } while (c.moveToNext());
        }
        return scenariosList;
    }

    public List<CaseClass> getAllCases() {
        List<CaseClass> casesList = new ArrayList<CaseClass>();
        String selectQuery = "SELECT  * FROM " + TABLE_CASE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                CaseClass caseClass = new CaseClass();
                caseClass.setId(c.getInt(c.getColumnIndex(KEY_CASE_ID)));
                caseClass.setText((c.getString(c.getColumnIndex(KEY_CASE_TEXT))));
                caseClass.setImageUrl(c.getString(c.getColumnIndex(KEY_CASE_IMAGE)));
                casesList.add(caseClass);
            } while (c.moveToNext());
        }
        return casesList;
    }
    public List<Answer> getAnswers() {
        List<Answer> answersList = new ArrayList<Answer>();
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWER;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setNewId(c.getInt(c.getColumnIndex(KEY_ANSWER_ID)));
                answer.setNewText((c.getString(c.getColumnIndex(KEY_ANSWER_TEXT))));
                answer.setNewCaseId(c.getInt(c.getColumnIndex(KEY_ANSWER_CASEID)));
                answersList.add(answer);
            } while (c.moveToNext());
        }
        return answersList;
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}