package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeMinimalisticSilver implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeMinimalisticSilver() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#ebeeee");
        colorRow.put(ThemeConstants.BACK_2, "#dbdfdf");
        colorRow.put(ThemeConstants.BACK_3, "#dbdfdf");
        colorRow.put(ThemeConstants.BACK_4, "#cdd1d1");
        colorRow.put(ThemeConstants.AC_1, "#fdfdfd");
        colorRow.put(ThemeConstants.AC_2, "#f6f6f6");
        colorRow.put(ThemeConstants.BACK_DARK, "#949a9a");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#828888");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#f6f6f6");
        colorRow.put(ThemeConstants.DEEP_DARK, "#dde6e6");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#98b6bb");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#ccd1d1");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#141319");
        colorRow.put(ThemeConstants.TEXT_DARK, "#141319");
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