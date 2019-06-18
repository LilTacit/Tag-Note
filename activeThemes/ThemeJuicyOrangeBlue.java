package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeJuicyOrangeBlue implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeJuicyOrangeBlue() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#dffdfa");
        colorRow.put(ThemeConstants.BACK_2, "#8dfaf2");
        colorRow.put(ThemeConstants.BACK_3, "#8dfaf2");
        colorRow.put(ThemeConstants.BACK_4, "#08ffdf");
        colorRow.put(ThemeConstants.AC_1, "#fe4307");
        colorRow.put(ThemeConstants.AC_2, "#fe2502");
        colorRow.put(ThemeConstants.BACK_DARK, "#34acc3");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#309eb4");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#ebf8f6");
        colorRow.put(ThemeConstants.DEEP_DARK, "#1c8aa0");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#d5c7ce");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#8ee0da");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#210c09");
        colorRow.put(ThemeConstants.TEXT_DARK, "#e7fefc");
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