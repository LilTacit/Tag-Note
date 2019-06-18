package com.mobile.kiril.tagnote.textWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.presenters.NotePresenter;
import com.mobile.kiril.tagnote.views.BaseView;
import com.mobile.kiril.tagnote.views.NoteView;

import java.util.Date;
import java.util.List;

public class NoteSaveTextWatcher implements TextWatcher {
    private EditText et;
    private DBHelper dbHelper;
    private BaseView baseView;
    private NoteView noteView;
    private NotePresenter notePresenter;

    public NoteSaveTextWatcher(DBHelper dbHelper, BaseView baseView, NoteView noteView, NotePresenter notePresenter, EditText et) {
        super();
        this.dbHelper = dbHelper;
        this.baseView = baseView;
        this.et = et;
        this.noteView = noteView;
        this.notePresenter = notePresenter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Note note = noteView.getNoteObject();

        String text, category, spotPath;
        text = note.getText();
        spotPath = note.getSpot();
        category = note.getCategory();

        List<Tag> tags = note.getTags();
        int id = note.getId();

        //LOG
        if(noteView.getIsExist()) {
            if(!text.equalsIgnoreCase("")) {
                notePresenter.updateNote(id, text, notePresenter.getTagStr(tags), category, new Date(), spotPath);
            } else {
                notePresenter.removeNote(id, category, notePresenter.getTagStr(tags), false);
                noteView.setIsExist(false);
            }
        } else {
            if(!text.equalsIgnoreCase("")) {
                notePresenter.addNote(text, notePresenter.getTagStr(tags), category, new Date(), spotPath);
                noteView.checkTagSave();
            }

            noteView.setIsExist(true);
        }
    }
}
