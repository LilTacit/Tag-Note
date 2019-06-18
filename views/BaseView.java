package com.mobile.kiril.tagnote.views;

import android.widget.LinearLayout;

import com.mobile.kiril.tagnote.toastEnum.ToastErrors;
import com.mobile.kiril.tagnote.toastEnum.ToastWarnings;
import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.themes.ThemeSetter;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

public interface BaseView {
    float dpToPixel(float dp);

    void showError(ToastErrors key);

    void showToast(ToastWarnings key, String subStr);

    void runCategorySearch(LinearLayout container, List<Category> categories);

    void runTagSearch(FlowLayout container, List<Tag> tags);

    void switchChangeModContainer(int number);

    void createThemeWrapsList();

    void buildTheme(ThemeSetter themeSetter);

    void createMapColor();
}
