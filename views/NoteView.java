package com.mobile.kiril.tagnote.views;

import com.mobile.kiril.tagnote.entities.Note;

public interface NoteView extends BaseView {
    Note getNoteObject();

    boolean getIsExist();

    void setIsExist(boolean is);

    void checkTagSave();

    void backToMainActivity();
}
