package com.mobile.kiril.tagnote.themes;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ThemeTestMock implements ThemeRow {
    private Map<String, String> testColorMap;
    private Map<String, Integer> integerMap;

    public ThemeTestMock() {
        testColorMap = new HashMap<>();

        testColorMap.put(ThemeConstants.BACK_1, "#4286f4");
        testColorMap.put(ThemeConstants.BACK_2, "#8f42f4");
        testColorMap.put(ThemeConstants.BACK_3, "#d442f4");
        testColorMap.put(ThemeConstants.BACK_4, "#42d9f4");
        testColorMap.put(ThemeConstants.AC_1, "#42f47d");
        testColorMap.put(ThemeConstants.AC_2, "#50f442");
        testColorMap.put(ThemeConstants.BACK_DARK, "#00ff0c");
        testColorMap.put(ThemeConstants.BACK_DARK_2, "#7f642d");
        testColorMap.put(ThemeConstants.BACK_DARK_LIGHT, "#1f1d70");
        testColorMap.put(ThemeConstants.DEEP_DARK, "#ff70cf");
        testColorMap.put(ThemeConstants.BACK_IC_COLOR, "#ff00f2");
        testColorMap.put(ThemeConstants.ERROR_COLOR, "#437f5c");
        testColorMap.put(ThemeConstants.HINT, "#437f5c");

        testColorMap.put(ThemeConstants.TEXT_LIGHT, "#4286f4");
        testColorMap.put(ThemeConstants.TEXT_DARK, "#00ff0c");
    }

    public Map<String, String> getColorMap() {
        return testColorMap;
    }

    public Map<String, Integer> getIntegerColorMap() {
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.clear();

        for(Map.Entry<String, String> entry : getColorMap().entrySet())
            integerMap.put(entry.getKey(), Color.parseColor(entry.getValue()));

        return integerMap;
    }
}