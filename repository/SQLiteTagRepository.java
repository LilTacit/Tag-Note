package com.mobile.kiril.tagnote.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.specifications.Specification;
import com.mobile.kiril.tagnote.specifications.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTagRepository implements Repository<Tag> {
    private DBHelper dbHelper;

    public SQLiteTagRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void add(Tag item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String name = item.getName();

        if(!name.equalsIgnoreCase("")) {
            contentValues.put(DBHelper.KEY_TAG_NAME, name);

            database.insert(DBHelper.TABLE_TAG, null, contentValues);

            Log.d("log", "Tag: " + name + " was added.");
        } else {
            Log.d("log", "Name error ----------");
        }
    }

    @Override
    public void remove(Tag item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String name = item.getName();

        if(!name.equalsIgnoreCase("")){
            int deleteCount = database.delete(DBHelper.TABLE_TAG, DBHelper.KEY_TAG_NAME + " = ?", new String[]{name});

            Log.d("log", String.format("Tags removed: %d. By params: name=%s.", deleteCount, name));
        } else {
            Log.d("log", "Name error ----------");
        }
    }

    @Override
    public void update(Tag item) {

    }

    @Override
    public List query(Specification specification) {
        List<Tag> tags = new ArrayList<>();
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), null);
        Log.d("log", "RawQuery String: " + sqlSpecification.toSqlQuery());

        if(cursor != null){
            if(cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TAG_NAME);

                do {
                    Tag tag = new Tag();

                    tag.setId(cursor.getInt(idIndex));
                    tag.setName(cursor.getString(nameIndex));

                    tags.add(tag);
                } while(cursor.moveToNext());
            } else {
                Log.d("log", "Cursor not to first in tag query");
            }
        } else {
            Log.d("log", "Cursor is null");
        }

        cursor.close();

        return tags;
    }
}
