package com.mobile.kiril.tagnote.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

public interface MainView extends BaseView {
    void showNotes(List<Note> notes, Context context, LinearLayout container);

    void showCategory(List<Category> categories, Context context, LinearLayout container);

    void showTag(List<Tag> tags, Context context, FlowLayout container);

    void moveMenu(int x, int y);

    void openMenu();

    void outFixMenu(int x, int y, boolean fixed);

    void closeMenu();

    void categoryClick(View v);

    void categoryLongPress(View v);

    void noteClick(View v);

    void noteLongPress(View v);

    void switchPopupContainer();

    void changeModUpdateCategory(String categoryName);

    void changeModRemoveCategory();

    void changeModTagAnimation(LinearLayout view, boolean isTo);

    void changeModUpdateTag(String tagStr);

    void changeModRemoveTag(String tagName, boolean isSingle);

//    void updateNoteAmount(String oldCategoryName, String newCategoryName);
}
