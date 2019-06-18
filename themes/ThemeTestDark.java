package com.mobile.kiril.tagnote.themes;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ThemeTestDark implements ThemeRow {
    private Map<String, String> testColorMap;
    private Map<String, Integer> integerMap;

    public ThemeTestDark() {
        testColorMap = new HashMap<>();

        testColorMap.put(ThemeConstants.BACK_1, "#424242");
        testColorMap.put(ThemeConstants.BACK_2, "#696969");
        testColorMap.put(ThemeConstants.BACK_3, "#282828");
        testColorMap.put(ThemeConstants.BACK_4, "#191919");
        testColorMap.put(ThemeConstants.AC_1, "#33a457");
        testColorMap.put(ThemeConstants.AC_2, "#347448");
        testColorMap.put(ThemeConstants.BACK_DARK, "#EFEFEF");
        testColorMap.put(ThemeConstants.BACK_DARK_2, "#f2f2f2");
        testColorMap.put(ThemeConstants.BACK_DARK_LIGHT, "#D7D7D7");
        testColorMap.put(ThemeConstants.DEEP_DARK, "#ffffff");
        testColorMap.put(ThemeConstants.BACK_IC_COLOR, "#F6F6F6");
        testColorMap.put(ThemeConstants.ERROR_COLOR, "#437f5c");
        testColorMap.put(ThemeConstants.HINT, "#4b4b4b");

        testColorMap.put(ThemeConstants.TEXT_LIGHT, "#EFEFEF");
        testColorMap.put(ThemeConstants.TEXT_DARK, "#424242");
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