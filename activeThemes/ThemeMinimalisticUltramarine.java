package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticUltramarine implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticUltramarine() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#f4f4fb");
        colorRow.put(ThemeConstants.BACK_3, "#f4f4fb");
        colorRow.put(ThemeConstants.BACK_4, "#dbdbf2");
        colorRow.put(ThemeConstants.AC_1, "#120a8f");
        colorRow.put(ThemeConstants.AC_2, "#000080");
        colorRow.put(ThemeConstants.BACK_DARK, "#2f2f3f");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#292937");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#e5e5ee");
        colorRow.put(ThemeConstants.DEEP_DARK, "#272735");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#434355");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#c7c7d6");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#252531");
        colorRow.put(ThemeConstants.TEXT_DARK, "#f4f4fb");
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