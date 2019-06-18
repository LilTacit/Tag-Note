package com.mobile.kiril.tagnote.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.specifications.ByNameSQLiteCategorySpecification;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteNoteSpecification;
import com.mobile.kiril.tagnote.specifications.Specification;
import com.mobile.kiril.tagnote.specifications.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class SQLiteNoteRepository implements Repository<Note> {
    private DBHelper dbHelper;

    public SQLiteNoteRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void add(Note item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String text, category, date, tag, spot;
        List<Tag> tagList = item.getTags();

        text = item.getText();

        if(!text.equalsIgnoreCase("")) {
            spot = item.getSpot();
            category = item.getCategory();
            date = item.getDate();
            tag = "";

            contentValues.put(DBHelper.KEY_NOTE_TEXT, text);
            contentValues.put(DBHelper.KEY_NOTE_DATE, date);

            try {
                if(tagList.size() > 0) {
                    for(Tag t : tagList) {
                        tag += t.getName() + "@@";
                    }
                    tag = tag.substring(0, tag.length()-2);

                    Log.d("log", "String-tag: " + tag);
                }
            } catch (NullPointerException e) {
                Log.d("log", "Tag`s, NullPointerException ---------");
            } catch (IndexOutOfBoundsException e2) {
                Log.d("log", "Tag`s, IndexOutOfBoundsException ---------");
            }

            try {
                contentValues.put(DBHelper.KEY_NOTE_SPOT, spot);
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_NOTE_SPOT, "");
            }

            try {
                if(!tag.equalsIgnoreCase("") || !tag.equalsIgnoreCase(null)) contentValues.put(DBHelper.KEY_NOTE_TAGS, tag);
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_NOTE_TAGS, "");
            }

            try {
                if(!category.equalsIgnoreCase("") || !category.equalsIgnoreCase(null)) contentValues.put(DBHelper.KEY_NOTE_CATEGORY, category);

                try {
                    SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

                    List<Category> categories = categoryRepository.query(new ByNameSQLiteCategorySpecification(category));

                    if(categories.size() > 0) {
                        Category upCategory = categories.get(0);

                        int noteAmount = upCategory.getNoteAmount();
                        upCategory.setNoteAmount(noteAmount + 1);

                        Log.d("log", String.format("NoteAmount = %d; UpNoteAmount = %d; Id = %d;", noteAmount, upCategory.getNoteAmount(), upCategory.getId()));

                        categoryRepository.update(upCategory);
                    }
                } catch (Exception e) {
                    Log.d("log", e.toString());
                }
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_NOTE_CATEGORY, "");
            }

            database.insert(DBHelper.TABLE_NOTE, null, contentValues);
        } else {
            Log.d("log", "Text is exist in addNote.");
        }
    }

    @Override
    public void remove(Note item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            List<Tag> tagList = item.getTags();
            if(tagList.size() != 0) {
                SQLiteTagRepository tagRepository = new SQLiteTagRepository(dbHelper);
                SQLiteNoteRepository noteRepository = new SQLiteNoteRepository(dbHelper);
                List<Note> allNotes = noteRepository.query(new ShowAllSQLiteNoteSpecification());

                for(Tag t : tagList) {
                    String tagName = t.getName();
                    int noteAmount = 0;

                    for(Note n : allNotes) {
                        List<Tag> tags = n.getTags();

                        for(Tag t2 : tags) {
                            if(t2.getName().equals(tagName)) {
                                noteAmount++;
                                break;
                            }
                        }
                    }

                    Log.d("log", tagName + ", amount: " + noteAmount);
                    if(noteAmount <= 1) tagRepository.remove(t);
                }
            }
        } catch (NullPointerException e) {}

        int id = item.getId();
        String category = item.getCategory();

        try {
            if(!category.equalsIgnoreCase("")) {
                SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

                List<Category> categories = categoryRepository.query(new ByNameSQLiteCategorySpecification(category));

                if(categories.size() > 0) {
                    Category upCategory = categories.get(0);

                    int noteAmount = upCategory.getNoteAmount();
                    upCategory.setNoteAmount(noteAmount - 1);

                    Log.d("log", String.format("NoteAmount = %d; UpNoteAmount = %d; Id = %d;", noteAmount, upCategory.getNoteAmount(), upCategory.getId()));

                    categoryRepository.update(upCategory);
                }
            }
        } catch (NullPointerException e) {
            Log.d("log", "NullPointerException in decriment category amount");
        }

        database.delete(DBHelper.TABLE_NOTE, String.format("%s = ?", DBHelper.KEY_ID), new String[]{Integer.toString(id)});
    }

    @Override
    public void update(Note item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String text, category, date, tag, spot;
        List<Tag> tagList = item.getTags();

        text = item.getText();
        int id = item.getId();

        if(!text.equalsIgnoreCase("")) {
            date = item.getDate();
            category = item.getCategory();
            spot = item.getSpot();
            tag = "";

            contentValues.put(DBHelper.KEY_NOTE_TEXT, text);
            contentValues.put(DBHelper.KEY_NOTE_DATE, date);

            try {
                contentValues.put(DBHelper.KEY_NOTE_SPOT, spot);
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_NOTE_SPOT, "");
            }

            try {
                if(!category.equalsIgnoreCase("") || !category.equalsIgnoreCase(null)) contentValues.put(DBHelper.KEY_NOTE_CATEGORY, category);
            } catch (NullPointerException e) {
                contentValues.put(DBHelper.KEY_CATEGORY_NAME, category);
            }

            try {
                if(tagList.size() > 0) {
                    for(Tag t : tagList)
                        tag += t.getName() + "@@";

                    tag = tag.substring(0, tag.length()-2);

                    Log.d("log", "String-tag: " + tag);

                    contentValues.put(DBHelper.KEY_NOTE_TAGS, tag);
                } else {
                    contentValues.put(DBHelper.KEY_NOTE_TAGS, "");
                }
            } catch (NullPointerException e) {
                Log.d("log", "Tag`s, NullPointerException ---------");
                contentValues.put(DBHelper.KEY_NOTE_TAGS, "");
            } catch (IndexOutOfBoundsException e2) {
                Log.d("log", "Tag`s, IndexOutOfBoundsException ---------");
                contentValues.put(DBHelper.KEY_NOTE_TAGS, "");
            }

            database.update(DBHelper.TABLE_NOTE, contentValues, String.format("%s = ?", DBHelper.KEY_ID), new String[]{Integer.toString(id)});
        } else {
            Log.d("log", "Text is exist in updateNote");
        }
    }

    @Override
    public List query(Specification specification) {
        List<Note> notes = new ArrayList<>();

        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), null);
        Log.d("log", "RawQuery String: " + sqlSpecification.toSqlQuery());

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int textIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE_TEXT);
                int spotIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE_SPOT);
                int categoryIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE_CATEGORY);
                int tagsIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE_TAGS);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE_DATE);

                do {
                    Note note = new Note();

                    int id = cursor.getInt(idIndex);
                    String text, spot, category, tagStr, date;
                    text = cursor.getString(textIndex);
                    spot = cursor.getString(spotIndex);
                    category = cursor.getString(categoryIndex);
                    tagStr = cursor.getString(tagsIndex);
                    date = cursor.getString(dateIndex);

                    note.setId(id);
                    note.setText(text);
                    note.setSpot(spot);
                    note.setCategory(category);
                    note.setDate(date);

                    List<Tag> tags = new ArrayList<>();

                    for(String s : tagStr.split("@@")){
                        Tag tag = new Tag();
                        tag.setName(s);

                        tags.add(tag);
                    }

                    note.setTags(tags);

                    notes.add(note);
                } while(cursor.moveToNext());
            } else {
                Log.d("log", "Cursor not to first --------");
            }
        } else {
            Log.d("log", "Cursor is null ------");
        }

        cursor.close();

        return notes;
    }
}
