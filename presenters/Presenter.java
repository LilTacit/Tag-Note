package com.mobile.kiril.tagnote.presenters;

import android.util.Log;

import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.repository.Repository;
import com.mobile.kiril.tagnote.repository.SQLiteCategoryRepository;
import com.mobile.kiril.tagnote.repository.SQLiteNoteRepository;
import com.mobile.kiril.tagnote.repository.SQLiteTagRepository;
import com.mobile.kiril.tagnote.specifications.ByCategorySQLiteNoteSpecification;
import com.mobile.kiril.tagnote.specifications.ByNameSQLiteCategorySpecification;
import com.mobile.kiril.tagnote.specifications.ByNameSQLiteTagSpecification;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteCategorySpecification;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteNoteSpecification;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteTagSpecification;
import com.mobile.kiril.tagnote.views.BaseView;

import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private Repository sqliteRepository;
    private BaseView baseView;
    private DBHelper dbHelper;

    public Presenter(BaseView baseView, DBHelper dbHelper) {
        this.baseView = baseView;
        this.dbHelper = dbHelper;
    }

    public boolean isValidateTagString(String tagStr) {
        if(!tagStr.equalsIgnoreCase("")) {
            if(tagStr.length() <= 44) {
                if (!tagStr.contains("@@")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void showAllNote() {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        List<Note> notes = sqliteRepository.query(new ShowAllSQLiteNoteSpecification());

        if(notes.size() > 0) {
            for(Note n : notes) {
                String tagStr = "";

                List<Tag> tags = n.getTags();

                if(tags != null) {
                    for (Tag t : tags) {
                        tagStr += t.getName() + ", ";
                    }

                    tagStr = tagStr.substring(0, tagStr.length()-2);
                }
            }
        } else {
            Log.d("log", "Table is exists ----------");
        }
    }

    public List<Note> getAllNotes() {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        List<Note> notes = sqliteRepository.query(new ShowAllSQLiteNoteSpecification());

        return notes;
    }

    public List<Category> getAllCategory() {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        List<Category> categories = sqliteRepository.query(new ShowAllSQLiteCategorySpecification());

        return categories;
    }

    public List<Tag> getAllTag() {
        sqliteRepository = new SQLiteTagRepository(dbHelper);

        List<Tag> tags = sqliteRepository.query(new ShowAllSQLiteTagSpecification());

        return tags;
    }

    public List<Note> getNoteByCategory(String category) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        List<Note> notes = sqliteRepository.query(new ByCategorySQLiteNoteSpecification(category));

        return notes;
    }

    public List<Note> getNoteByTag(String tag) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        List<Note> notes = new ArrayList<>();
//        List<Note> notes = sqliteRepository.query(new ByTagSQLiteNoteSpecification(tag));
        List<Note> allNotes = getAllNotes();

        for(Note n : allNotes) {
            List<Tag> tags = n.getTags();

            for(Tag t : tags) {
                if(t.getName().equals(tag)) {
                    notes.add(n);
                    break;
                }
            }
        }

        return notes;
    }

    public List<Tag> getTagByName(String tagName) {
        sqliteRepository = new SQLiteTagRepository(dbHelper);

        List<Tag> tags = sqliteRepository.query(new ByNameSQLiteTagSpecification(tagName));

        return tags;
    }

    public void updateNoteAmount(String oldCategoryName, String newCategoryName) {
        SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

        List<Category> oldCategories = categoryRepository.query(new ByNameSQLiteCategorySpecification(oldCategoryName));
        List<Category> newCategories = categoryRepository.query(new ByNameSQLiteCategorySpecification(newCategoryName));

        if(oldCategories.size() > 0) {
            Category c = oldCategories.get(0);
            c.setNoteAmount(c.getNoteAmount() - 1);
            categoryRepository.update(c);
        }

        if(!newCategoryName.equals("")) {
            if(newCategories.size() > 0) {
                Category c = newCategories.get(0);
                c.setNoteAmount(c.getNoteAmount() + 1);
                categoryRepository.update(c);
            }
        }
    }

    public void checkCategoryExist(String categoryName) {
        SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

        List<Category> newCategories = categoryRepository.query(new ByNameSQLiteCategorySpecification(categoryName));

        if(newCategories.size() == 0) {
            Category c = new Category();
            c.setName(categoryName);

            categoryRepository.add(c);
        }
    }

    public String getTagStr(List<Tag> tags) {
        String str = "";

        for(Tag t : tags) {
            String tagName = t.getName();
            if(!tagName.equalsIgnoreCase("")) str += tagName + "@@";
        }

        if (str.length() > 0) str = str.substring(0, str.length()-2);

        return str;
    }

    public String getTagStr(String[] tagNames) {
        String str = "";

        for (int i = 0; i < tagNames.length ; i++) {
            String tagName = tagNames[i];
            if(!tagName.equalsIgnoreCase("")) str += tagName + "@@";
        }

        if (str.length() > 0) str = str.substring(0, str.length()-2);

        return str;
    }

    public List<Tag> getTagListFromStr(String tagStr) {
        List<Tag> tags = new ArrayList<>();

        if(!tagStr.equalsIgnoreCase("")) {
            for(String s : tagStr.split("@@")) {
                if(!s.equalsIgnoreCase("")) {
                    Tag tag = new Tag();
                    tag.setName(s);
                    tags.add(tag);
                }
            }
        }

        return tags;
    }

    public void logAllNotes() {
        List<Note> notes = getAllNotes();

        for(Note n : notes) {
            Log.d("log", String.format("Note #%d: text = '%s', category = '%s', spotPath = '%s'", n.getId(), n.getText(), n.getCategory(), n.getSpot()));

            List<Tag> tags = n.getTags();

            if(tags.size() > 0) {
                Log.d("log", String.format("Note #%d tags:", n.getId()));

                int count = 0;
                for(Tag t : tags) {
                    Log.d("log", String.format("Tag #%d: %s", count, t.getName()));
                    count++;
                }
            }
        }
    }
}
