package com.mobile.kiril.tagnote.specifications;

import com.mobile.kiril.tagnote.fordatabase.DBHelper;

public class ByTextSQLiteNoteSpecification implements SqlSpecification {
    private String text;

    public ByTextSQLiteNoteSpecification(String text) {
        this.text = text;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %s WHERE %s='%s'",
                DBHelper.TABLE_NOTE,
                DBHelper.KEY_NOTE_TEXT,
                text
        );
    }
}
