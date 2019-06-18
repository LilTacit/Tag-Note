package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeDarkBlue implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeDarkBlue() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#282828");
        colorRow.put(ThemeConstants.BACK_2, "#333333");
        colorRow.put(ThemeConstants.BACK_3, "#373737");
        colorRow.put(ThemeConstants.BACK_4, "#3f3f3f");
        colorRow.put(ThemeConstants.AC_1, "#1faee9");
        colorRow.put(ThemeConstants.AC_2, "#138abd");
        colorRow.put(ThemeConstants.BACK_DARK, "#f5f4f4");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#dedede");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#232323");
        colorRow.put(ThemeConstants.DEEP_DARK, "#ffffff");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#4d4d4d");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#424242");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#FCFCFC");
        colorRow.put(ThemeConstants.TEXT_DARK, "#282828");
    }

    public Map<String, String> getColorMap() {
        return colorRow;
    }

    public Map<String, Integer> getIntegerColorMap() {
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.clear();

        for(Map.Entry<String, String> entry : getColorMap().entrySet())
            integerMap.put(entry.getKey(), Color.parseColor(entry.getValue()));

        return integerMap;
    }
}