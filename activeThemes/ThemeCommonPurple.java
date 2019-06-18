package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeCommonPurple implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeCommonPurple() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#FCFCFC");
        colorRow.put(ThemeConstants.BACK_2, "#F6F6F6");
        colorRow.put(ThemeConstants.BACK_3, "#EFEFEF");
        colorRow.put(ThemeConstants.BACK_4, "#E2E2E2");
        colorRow.put(ThemeConstants.AC_1, "#6c0091");
        colorRow.put(ThemeConstants.AC_2, "#5c007b");
        colorRow.put(ThemeConstants.BACK_DARK, "#282828");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#424242");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#D7D7D7");
        colorRow.put(ThemeConstants.DEEP_DARK, "#1E1E1E");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#696969");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#C8C8C8");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#282828");
        colorRow.put(ThemeConstants.TEXT_DARK, "#FCFCFC");
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