package com.mobile.kiril.tagnote.presenters;

import android.util.Log;

import com.mobile.kiril.tagnote.toastEnum.ToastErrors;
import com.mobile.kiril.tagnote.toastEnum.ToastWarnings;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.repository.Repository;
import com.mobile.kiril.tagnote.repository.SQLiteNoteRepository;
import com.mobile.kiril.tagnote.repository.SQLiteTagRepository;
import com.mobile.kiril.tagnote.specifications.ByNameSQLiteTagSpecification;
import com.mobile.kiril.tagnote.specifications.ByTextSQLiteNoteSpecification;
import com.mobile.kiril.tagnote.views.BaseView;
import com.mobile.kiril.tagnote.views.NoteView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotePresenter extends Presenter {
    private NoteView noteView;
    private DBHelper dbHelper;
    private Repository sqliteRepository;

    public NotePresenter(BaseView baseView, DBHelper dbHelper) {
        super(baseView, dbHelper);
        this.noteView = (NoteView) baseView;
        this.dbHelper = dbHelper;
    }

    public void addNote(String text, String tag, String category, Date date, String spot) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        if(!text.equalsIgnoreCase("")) {
            Note note = new Note();
            note.setText(text);
            note.setCategory(category);
            note.setSpot(spot);

            if(!tag.equalsIgnoreCase("")) {
                List<Tag> tags = new ArrayList<>();
                Tag tag1 = new Tag();
                tag1.setName(tag);
                tags.add(tag1);

                note.setTags(tags);
            }

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy");
            String dateStr = simpleDate.format(date);
            note.setDate(dateStr);

            sqliteRepository.add(note);
        } else {
            Log.d("log", "Text error in add note.");
        }
    }

    public void updateNote(int id, String text, String tag, String category, Date date, String spot) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        if(!text.equalsIgnoreCase("")) {
            Note note = new Note();
            note.setText(text);
            note.setCategory(category);
            note.setId(id);
            note.setSpot(spot);

            if(!tag.equalsIgnoreCase("")) {
                note.setTags(getTagListFromStr(tag));
            }

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy");
            String dateStr = simpleDate.format(date);
            note.setDate(dateStr);

            sqliteRepository.update(note);

//            noteView.showToast("Note updated");
        } else {
            Log.d("log", "Text error in update note.");
        }
    }

    public void removeNote(int id, String category, String tag, boolean withAlert) {
        sqliteRepository = new SQLiteNoteRepository(dbHelper);

        Note note = new Note();
        note.setId(id);
        note.setCategory(category);
        note.setTags(getTagListFromStr(tag));

        sqliteRepository.remove(note);

        if(withAlert) noteView.showToast(ToastWarnings.NOTE_REMOVED, null);
    }

    public void addTag(String name, boolean withAlert) {
        sqliteRepository = new SQLiteTagRepository(dbHelper);

        if(!name.equalsIgnoreCase("")) {
            if(name.length() > 44) {
                noteView.showError(ToastErrors.LONGER);
            } else {
                if (name.contains("@@")) {
                    noteView.showError(ToastErrors.INVALID_CHARACTER_COMBINATION);
                } else {
                    name = name.replaceAll("\n", " ");

                    Tag tag = new Tag();
                    tag.setName(name);

                    sqliteRepository.add(tag);

                    if(withAlert) noteView.showToast(ToastWarnings.ADD_TAG_WITH_NAME, name);
                }
            }
        } else {
            Log.d("log", "Name error in added tag.");
        }
    }

    public void removeTag(String tagName) {
        if(!tagName.equalsIgnoreCase("")) {
            sqliteRepository = new SQLiteTagRepository(dbHelper);

            Tag tag = new Tag();
            tag.setName(tagName);

            sqliteRepository.remove(tag);
        }
    }

    public List<Tag> getTagByName (String name) {
        SQLiteTagRepository repository = new SQLiteTagRepository(dbHelper);

        List<Tag> tags = repository.query(new ByNameSQLiteTagSpecification(name));

        return tags;
    }

    public List<Note> getNoteByText (String text) {
        SQLiteNoteRepository repository = new SQLiteNoteRepository(dbHelper);

        List<Note> notes = repository.query(new ByTextSQLiteNoteSpecification(text));

        return notes;
    }
}
