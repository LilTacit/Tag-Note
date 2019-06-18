package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeJuicyRed implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeJuicyRed() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#fe0000");
        colorRow.put(ThemeConstants.BACK_3, "#fe0000");
        colorRow.put(ThemeConstants.BACK_4, "#f9f9f9");
        colorRow.put(ThemeConstants.AC_1, "#c30000");
        colorRow.put(ThemeConstants.AC_2, "#b20000");
        colorRow.put(ThemeConstants.BACK_DARK, "#282828");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#232323");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#f5f5f5");
        colorRow.put(ThemeConstants.DEEP_DARK, "#212121");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#373739");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#ff00ba");
        colorRow.put(ThemeConstants.HINT, "#acacac");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#000000");
        colorRow.put(ThemeConstants.TEXT_DARK, "#ffffff");
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