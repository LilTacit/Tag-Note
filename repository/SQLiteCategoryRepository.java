package com.mobile.kiril.tagnote.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.specifications.Specification;
import com.mobile.kiril.tagnote.specifications.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class SQLiteCategoryRepository implements Repository<Category> {
    private DBHelper dbHelper;

    public SQLiteCategoryRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void add(Category item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String name = item.getName();
        String spot = item.getSpot();
        int amount = item.getNoteAmount();

        if(!name.equalsIgnoreCase("")) {
            contentValues.put(DBHelper.KEY_CATEGORY_NAME, name);

            try {
                contentValues.put(DBHelper.KEY_CATEGORY_SPOT, spot);
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_CATEGORY_SPOT, "");
            }

            contentValues.put(DBHelper.KEY_CATEGORY_AMOUNT, amount);

            database.insert(DBHelper.TABLE_CATEGORY, null, contentValues);
            Log.d("log", String.format("Category %s with spot: %s, added.", name, spot));
        } else {
            Log.d("Log", "Name error ---------");
        }

        database.close();
    }

    @Override
    public void remove(Category item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int id = item.getId();

        database.delete(DBHelper.TABLE_CATEGORY, String.format("%s = ?", DBHelper.KEY_ID), new String[]{Integer.toString(id)});

        database.close();
    }

    @Override
    public void update(Category item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int id = item.getId();

        Log.d("log", String.format("Category #%d: Name: %s, Spot: %s, NoteAmount: %d.", item.getId(), item.getName(), item.getSpot(), item.getNoteAmount()));

        if(id != 0) {
            ContentValues contentValues = new ContentValues();

            String name = item.getName();
            String spot = item.getSpot();

            if(!name.equalsIgnoreCase("")) {
                contentValues.put(DBHelper.KEY_CATEGORY_NAME, name);

                try {
                    contentValues.put(DBHelper.KEY_CATEGORY_SPOT, spot);
                } catch (NullPointerException e) {
                    contentValues.put(DBHelper.KEY_CATEGORY_SPOT, "");
                }

                contentValues.put(DBHelper.KEY_CATEGORY_AMOUNT, item.getNoteAmount());

                String idStr = Integer.toString(id);
                database.update(DBHelper.TABLE_CATEGORY, contentValues, DBHelper.KEY_ID + " = ?", new String[]{idStr});
            } else {
                Log.d("log", "Update category: name error -----------");
            }
        }

//        database.close();
//        Log.d("log", "Update category process ------");
    }

    @Override
    public List query(Specification specification) {
        List<Category> categories = new ArrayList<>();
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), null);
        Log.d("log", "RawQuery String: " + sqlSpecification.toSqlQuery());

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_CATEGORY_NAME);
                int spotIndex = cursor.getColumnIndex(DBHelper.KEY_CATEGORY_SPOT);
                int amountIndex = cursor.getColumnIndex(DBHelper.KEY_CATEGORY_AMOUNT);

                do {
                    Category category = new Category();

                    category.setId(cursor.getInt(idIndex));
                    category.setName(cursor.getString(nameIndex));
                    category.setSpot(cursor.getString(spotIndex));
                    category.setNoteAmount(cursor.getInt(amountIndex));

                    categories.add(category);
                } while(cursor.moveToNext());
            } else {
                Log.d("log", "Cursor not to first --------");
            }
        } else {
            Log.d("log", "Cursor is null ------");
        }

        if(cursor != null && !cursor.isClosed()) cursor.close();

        return categories;
    }
}
