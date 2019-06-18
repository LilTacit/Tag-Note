package com.mobile.kiril.tagnote.fordatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "_id";

    //Category key
    public static final String KEY_CATEGORY_NAME = "name";
    public static final String KEY_CATEGORY_SPOT = "spot";
    public static final String KEY_CATEGORY_AMOUNT = "amount";

    //Tag key
    public static final String KEY_TAG_NAME = "name";

    //Note key
    public static final String KEY_NOTE_TEXT = "text";
    public static final String KEY_NOTE_TAGS = "tags";
    public static final String KEY_NOTE_SPOT = "spot";
    public static final String KEY_NOTE_CATEGORY = "category";
    public static final String KEY_NOTE_DATE = "date";

    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_TAG = "tags";
    public static final String TABLE_NOTE = "notes";

    public static final String DATABASE_NAME = "appData";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s (%s integer primary key autoincrement, %s text, %s integer, %s integer);",
                TABLE_CATEGORY, KEY_ID, KEY_CATEGORY_NAME, KEY_CATEGORY_AMOUNT, KEY_CATEGORY_SPOT));
        db.execSQL(String.format("create table %s(%s integer primary key,%s text)",
                TABLE_TAG, KEY_ID, KEY_TAG_NAME));
        db.execSQL(String.format("create table %s(%s integer primary key,%s text,%s text,%s integer,%s text,%s text)",
                TABLE_NOTE, KEY_ID, KEY_NOTE_TEXT, KEY_NOTE_TAGS, KEY_NOTE_SPOT, KEY_NOTE_CATEGORY, KEY_NOTE_DATE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CATEGORY);
        db.execSQL("drop table if exists " + TABLE_TAG);
        db.execSQL("drop table if exists " + TABLE_NOTE);

        onCreate(db);
    }
}
