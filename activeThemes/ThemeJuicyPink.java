package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeJuicyPink implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeJuicyPink() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#ff0ee8");
        colorRow.put(ThemeConstants.BACK_3, "#ff0ee8");
        colorRow.put(ThemeConstants.BACK_4, "#f9f9f9");
        colorRow.put(ThemeConstants.AC_1, "#ff0079");
        colorRow.put(ThemeConstants.AC_2, "#d90067");
        colorRow.put(ThemeConstants.BACK_DARK, "#2a262a");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#252225");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#f5f5f5");
        colorRow.put(ThemeConstants.DEEP_DARK, "#212121");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#423b44");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#b99db9");

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