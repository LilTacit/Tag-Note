package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeJuicyGreen implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeJuicyGreen() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ffffff");
        colorRow.put(ThemeConstants.BACK_2, "#00ff51");
        colorRow.put(ThemeConstants.BACK_3, "#00ff51");
        colorRow.put(ThemeConstants.BACK_4, "#f9f9f9");
        colorRow.put(ThemeConstants.AC_1, "#00cc63");
        colorRow.put(ThemeConstants.AC_2, "#00bb5a");
        colorRow.put(ThemeConstants.BACK_DARK, "#182624");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#162321");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#e7f7e9");
        colorRow.put(ThemeConstants.DEEP_DARK, "#14201f");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#273d3a");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#a9eeb1");

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