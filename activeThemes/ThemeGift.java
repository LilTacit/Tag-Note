package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeGift implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeGift() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#fffcea");
        colorRow.put(ThemeConstants.BACK_2, "#fff5c6");
        colorRow.put(ThemeConstants.BACK_3, "#fff5c6");
        colorRow.put(ThemeConstants.BACK_4, "#edda9e");
        colorRow.put(ThemeConstants.AC_1, "#ffcc00");
        colorRow.put(ThemeConstants.AC_2, "#e5b700");
        colorRow.put(ThemeConstants.BACK_DARK, "#23201d");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#181614");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#edda9e");
        colorRow.put(ThemeConstants.DEEP_DARK, "#0c0b0a");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#625a51");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#b2a378");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#13100d");
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