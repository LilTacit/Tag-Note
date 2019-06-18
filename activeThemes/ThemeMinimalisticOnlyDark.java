package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticOnlyDark implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticOnlyDark() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#121215");
        colorRow.put(ThemeConstants.BACK_2, "#0d0d10");
        colorRow.put(ThemeConstants.BACK_3, "#0d0d10");
        colorRow.put(ThemeConstants.BACK_4, "#0b0b0e");
        colorRow.put(ThemeConstants.AC_1, "#1d1c24");
        colorRow.put(ThemeConstants.AC_2, "#212029");
        colorRow.put(ThemeConstants.BACK_DARK, "#000000");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#070609");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#111114");
        colorRow.put(ThemeConstants.DEEP_DARK, "#09080c");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#1d1b25");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#18181c");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#e4e4e4");
        colorRow.put(ThemeConstants.TEXT_DARK, "#e4e4e4");
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