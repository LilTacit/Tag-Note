package com.mobile.kiril.tagnote.textWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.repository.SQLiteTagRepository;
import com.mobile.kiril.tagnote.specifications.ShowAllSQLiteTagSpecification;
import com.mobile.kiril.tagnote.views.BaseView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchTagTextWatcher implements TextWatcher {
    private EditText et;
    private DBHelper dbHelper;
    private BaseView baseView;
    private FlowLayout container;

    public SearchTagTextWatcher(DBHelper dbHelper, BaseView baseView, FlowLayout container, EditText et) {
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
        baseView.runTagSearch(container, searchListTag(et.getText().toString()));
    }

    public List<Tag> searchListTag(String stringBit) {
        List<Tag> tags = new ArrayList<>();
        SQLiteTagRepository tagRepository = new SQLiteTagRepository(dbHelper);

        List<Tag> allTags = tagRepository.query(new ShowAllSQLiteTagSpecification());

        for(Tag t : allTags) {
            String tagName = t.getName();

            if(!(tagName.length() < stringBit.length())) {
                if(tagName.substring(0, stringBit.length()).equals(stringBit)) tags.add(t);
            }
        }

        return tags;
    }
}
