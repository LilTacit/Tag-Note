package com.mobile.kiril.tagnote.presenters;

import android.util.Log;

import com.mobile.kiril.tagnote.toastEnum.ToastErrors;
import com.mobile.kiril.tagnote.toastEnum.ToastWarnings;
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
import com.mobile.kiril.tagnote.views.BaseView;
import com.mobile.kiril.tagnote.views.MainView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPresenter extends Presenter {
    private MainView mainView;
    private DBHelper dbHelper;
    private Repository sqliteRepository;

    public MainPresenter(BaseView baseView, DBHelper dbHelper) {
        super(baseView, dbHelper);
        this.mainView = (MainView) baseView;
        this.dbHelper = dbHelper;
    }

    //Category presenter ---------------
    public void addCategory(String name, String spot, int noteAmount) {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        Category category = new Category();

        if(isValidateCategoryName(name)) {
            name = name.replaceAll("\n", " ");

            category.setName(name);
            category.setSpot(spot);
            category.setNoteAmount(noteAmount);

            sqliteRepository.add(category);

            mainView.showToast(ToastWarnings.ADD_CATEGORY_WITH_NAME, name);
        }
    }

    public void updateCategory(int id, String name, String spot, int noteAmount) {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        Category category = new Category();

        if(isValidateCategoryName(name)) {
            name = name.replaceAll("\n", " ");

            category.setId(id);
            category.setName(name);
            category.setSpot(spot);
            category.setNoteAmount(noteAmount);

            sqliteRepository.update(category);

//            mainView.showToast("Category '" + name + "' updated");
        }
    }

    public void updateCategorySpot(Category c, String spot) {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        c.setSpot(spot);

        sqliteRepository.update(c);
    }

    public void removeCategory(int id, String name) {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        Category category = new Category();

        if(!name.equalsIgnoreCase("")){
            category.setId(id);
            category.setName(name);

            sqliteRepository.remove(category);
        } else {
            Log.d("log", "Name error --------");
        }
    }

    public void clearCategoryInNote(String categoryName) {
        List<Note> notes = getNoteByCategory(categoryName);

        for (Note n : notes)
            updateNote(n.getId(), n.getText(), getTagStr(n.getTags()), "", new Date(), n.getSpot());
    }

    public void updateCategoryNameInNotes(String oldName, String newName) {
        if(!oldName.equalsIgnoreCase(newName)) {
            sqliteRepository = new SQLiteNoteRepository(dbHelper);
            List<Note> notes = sqliteRepository.query(new ByCategorySQLiteNoteSpecification(oldName));

            for (Note n : notes) {
                n.setCategory(newName);
                sqliteRepository.update(n);
            }
        }
    }

    public void mixCategory(List<Category> categories, String newName) {
        if(categories.size() > 1) {
            if(isValidateCategoryName(newName)) {
                sqliteRepository = new SQLiteNoteRepository(dbHelper);
                SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

                Category mainCategory = categories.get(0);
                int countNotes = 0;

                for (int i = 0; i < categories.size(); i++) {
                    Category c = categories.get(i);
                    String categoryName = c.getName();
                    List<Note> notes = sqliteRepository.query(new ByCategorySQLiteNoteSpecification(categoryName));
                    countNotes += notes.size();

                    for(Note n : notes) {
                        n.setCategory(newName);
                        sqliteRepository.update(n);
                    }

                    if(i != 0) categoryRepository.remove(c);
                }

                mainCategory.setName(newName);
                mainCategory.setNoteAmount(countNotes);
                categoryRepository.update(mainCategory);
            }
        }
    }

        //Tag presenter ---------------
    public void addTag(String name) {
        sqliteRepository = new SQLiteTagRepository(dbHelper);

        if(!name.equalsIgnoreCase("")) {
            Tag tag = new Tag();
            tag.setName(name);

            sqliteRepository.add(tag);
        } else {
            Log.d("log", "Name error ---------");
        }
    }

    public void removeTag(String name) {
        sqliteRepository = new SQLiteTagRepository(dbHelper);

        if(!name.equalsIgnoreCase("")) {
            Tag tag = new Tag();
            tag.setName(name);

            sqliteRepository.remove(tag);
        } else {
            Log.d("log", "Name error ---------");
        }
    }

        //Note presenter --------------
    public void removeNote(int id, String category, String tag) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        Note note = new Note();
        note.setId(id);
        note.setCategory(category);
        note.setTags(getTagListFromStr(tag));

        sqliteRepository.remove(note);

//        mainView.showToast("Note removed");
    }

    public void updateNote(int id, String text, String tag, String category, Date date, String spot) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        if(!text.equalsIgnoreCase("")) {
            Note note = new Note();
            note.setText(text);
            note.setCategory(category);
            note.setId(id);

            if(!tag.equalsIgnoreCase("")) {
                List<Tag> tags = new ArrayList<>();

                for(String s : tag.split("@@")) {
                    Tag t = new Tag();
                    t.setName(s);

                    tags.add(t);
                }

                note.setTags(tags);
            }

            note.setSpot(spot);

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy");
            String dateStr = simpleDate.format(date);
            note.setDate(dateStr);

            sqliteRepository.update(note);

//            mainView.showToast("Note updated");
        } else {
            Log.d("log", "Text error in update note.");
        }
    }

    public boolean isValidateCategoryName(String name) {
        sqliteRepository = new SQLiteCategoryRepository(dbHelper);

        if(!name.equalsIgnoreCase("")) {
            if(name.length() > 44) {
                mainView.showError(ToastErrors.LONGER);
                return false;
            } else {
                if(sqliteRepository.query(new ByNameSQLiteCategorySpecification(name)).size() == 0) {
                    return true;
                } else {
                    mainView.showError(ToastErrors.CATEGORY_EXIST);
                    return false;
                }
            }

        } else {

            mainView.showError(ToastErrors.NAME_IS_EMPTY);

            return false;
        }
    }
}
