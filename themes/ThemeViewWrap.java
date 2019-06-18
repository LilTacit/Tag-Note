package com.mobile.kiril.tagnote.themes;

import android.view.View;

public class ThemeViewWrap {
    View view;
    String color;
    ThemeWrapType type;

    public ThemeViewWrap(View view, ThemeWrapType type, String color) {
        this.view = view;
        this.color = color;
        this.type = type;
    }
}
