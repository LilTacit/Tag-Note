package com.mobile.kiril.tagnote.specifications;

import com.mobile.kiril.tagnote.fordatabase.DBHelper;

public class ByCategorySQLiteNoteSpecification implements SqlSpecification {
    private String name;

    public ByCategorySQLiteNoteSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %s WHERE %s='%s'",
                DBHelper.TABLE_NOTE,
                DBHelper.KEY_NOTE_CATEGORY,
                name
        );
    }
}
