package com.example.user.humanexpert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 13.10.2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String LOG = "DBHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "humanExpert";

    public static final String TABLE_SCEN = "scenarios";
    public static final String TABLE_CASE = "cases";
    public static final String TABLE_ANSWER = "answers";

    public static final String KEY_SCEN_ID = "id";
    public static final String KEY_SCEN_TEXT = "text";
    public static final String KEY_SCEN_CASEID = "caseId";

    public static final String KEY_CASE_ID = "id";
    public static final String KEY_CASE_TEXT = "text";
    public static final String KEY_CASE_IMAGE = "image";

    public static final String KEY_ANSWER_ID = "id";
    public static final String KEY_ANSWER_TEXT = "text";
    public static final String KEY_ANSWER_CASEID = "caseId";

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
}