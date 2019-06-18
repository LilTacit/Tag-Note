package com.mobile.kiril.tagnote.specifications;

import com.mobile.kiril.tagnote.fordatabase.DBHelper;

public class ShowAllSQLiteNoteSpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %s GROUP BY %s",
                DBHelper.TABLE_NOTE,
                DBHelper.KEY_ID
        );
    }
}
