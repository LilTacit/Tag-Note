package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticBrown implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticBrown() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#927561");
        colorRow.put(ThemeConstants.BACK_2, "#53453a");
        colorRow.put(ThemeConstants.BACK_3, "#53453a");
        colorRow.put(ThemeConstants.BACK_4, "#453930");
        colorRow.put(ThemeConstants.AC_1, "#f1f6f7");
        colorRow.put(ThemeConstants.AC_2, "#e2eaec");
        colorRow.put(ThemeConstants.BACK_DARK, "#2e2e30");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#29292b");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#b89d8b");
        colorRow.put(ThemeConstants.DEEP_DARK, "#232325");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#474749");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#b89d8b");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#fbfbfc");
        colorRow.put(ThemeConstants.TEXT_DARK, "#fbfbfc");
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