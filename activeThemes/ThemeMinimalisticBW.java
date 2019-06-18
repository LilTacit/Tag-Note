package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticBW implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticBW() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#ececec");
        colorRow.put(ThemeConstants.BACK_3, "#ececec");
        colorRow.put(ThemeConstants.BACK_4, "#dddddd");
        colorRow.put(ThemeConstants.AC_1, "#424242");
        colorRow.put(ThemeConstants.AC_2, "#3b3b3b");
        colorRow.put(ThemeConstants.BACK_DARK, "#c7c7c7");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#b2b2b2");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#f1f1f1");
        colorRow.put(ThemeConstants.DEEP_DARK, "#a6a6a6");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#747474");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#d2d2d2");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#282828");
        colorRow.put(ThemeConstants.TEXT_DARK, "#2e2e2e");
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