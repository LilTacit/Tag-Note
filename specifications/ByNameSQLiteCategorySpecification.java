package com.mobile.kiril.tagnote.specifications;

import com.mobile.kiril.tagnote.fordatabase.DBHelper;

public class ByNameSQLiteCategorySpecification implements SqlSpecification {
    private String name;

    public ByNameSQLiteCategorySpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %s WHERE %s='%s'",
                DBHelper.TABLE_CATEGORY,
                DBHelper.KEY_CATEGORY_NAME,
                name
        );
    }
}
