package com.mobile.kiril.tagnote.specifications;

import com.mobile.kiril.tagnote.fordatabase.DBHelper;

public class ByNameSQLiteTagSpecification implements SqlSpecification {
    private String name;

    public ByNameSQLiteTagSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %s WHERE %s='%s'",
                DBHelper.TABLE_TAG,
                DBHelper.KEY_TAG_NAME,
                name
        );
    }
}
