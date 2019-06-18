package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticOld implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticOld() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#f5f5e4");
        colorRow.put(ThemeConstants.BACK_3, "#f5f5e4");
        colorRow.put(ThemeConstants.BACK_4, "#e3e3c7");
        colorRow.put(ThemeConstants.AC_1, "#406432");
        colorRow.put(ThemeConstants.AC_2, "#38582c");
        colorRow.put(ThemeConstants.BACK_DARK, "#a3b882");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#90a373");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#ffffeb");
        colorRow.put(ThemeConstants.DEEP_DARK, "#b9cb91");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#e1d1c5");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#e7e7df");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#282828");
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