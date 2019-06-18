package com.mobile.kiril.tagnote.activeThemes;

import android.graphics.Color;

import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeRow;

import java.util.HashMap;
import java.util.Map;

public class ThemeJuicyPinkPurple implements ThemeRow {
    private Map<String, String> colorRow;
    private Map<String, Integer> integerMap;

    public ThemeJuicyPinkPurple() {
        colorRow = new HashMap<>();

        colorRow.put(ThemeConstants.BACK_1, "#272027");
        colorRow.put(ThemeConstants.BACK_2, "#582880");
        colorRow.put(ThemeConstants.BACK_3, "#582880");
        colorRow.put(ThemeConstants.BACK_4, "#2f1842");
        colorRow.put(ThemeConstants.AC_1, "#fe84e1");
        colorRow.put(ThemeConstants.AC_2, "#fc74ce");
        colorRow.put(ThemeConstants.BACK_DARK, "#ffc7fa");
        colorRow.put(ThemeConstants.BACK_DARK_2, "#ffcefb");
        colorRow.put(ThemeConstants.BACK_DARK_LIGHT, "#c281bc");
        colorRow.put(ThemeConstants.DEEP_DARK, "#e5b3e1");
        colorRow.put(ThemeConstants.BACK_IC_COLOR, "#d3b4f8");
        colorRow.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        colorRow.put(ThemeConstants.HINT, "#766188");

        colorRow.put(ThemeConstants.TEXT_LIGHT, "#ffeafd");
        colorRow.put(ThemeConstants.TEXT_DARK, "#2c242c");
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