package com.mobile.kiril.tagnote.textWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.repository.SQLiteCategoryRepository;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteCategorySpecification;
import com.mobile.kiril.tagnote.views.BaseView;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryTextWatcher implements TextWatcher {
    private EditText et;
    private DBHelper dbHelper;
    private BaseView baseView;
    private LinearLayout container;

    public SearchCategoryTextWatcher(DBHelper dbHelper, BaseView baseView, LinearLayout container, EditText et) {
        super();
        this.dbHelper = dbHelper;
        this.container = container;
        this.baseView = baseView;
        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        baseView.runCategorySearch(container, searchListCategory(et.getText().toString()));
        /*for(Category c : searchListCategory(et.getText().toString()))
            Log.d("log", c.getName());*/
    }

    public List<Category> searchListCategory(String stringBit) {
        List<Category> categories = new ArrayList<>();
        SQLiteCategoryRepository categoryRepository = new SQLiteCategoryRepository(dbHelper);

        List<Category> allCategories = categoryRepository.query(new ShowAllSQLiteCategorySpecification());

        for(Category c : allCategories) {
            String categoryName = c.getName();

            if(!(categoryName.length() < stringBit.length())) {
                if(categoryName.substring(0, stringBit.length()).equals(stringBit)) categories.add(c);
            }
        }

        return categories;
    }
}
