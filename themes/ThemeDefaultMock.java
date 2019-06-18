package com.mobile.kiril.tagnote.themes;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ThemeDefaultMock implements ThemeRow {
    private Map<String, String> defaultColorMap;
    private Map<String, Integer> integerMap;

    public ThemeDefaultMock() {
        defaultColorMap = new HashMap<>();

        defaultColorMap.put(ThemeConstants.BACK_1, "#FCFCFC");
        defaultColorMap.put(ThemeConstants.BACK_2, "#F6F6F6");
        defaultColorMap.put(ThemeConstants.BACK_3, "#EFEFEF");
        defaultColorMap.put(ThemeConstants.BACK_4, "#E2E2E2");
        defaultColorMap.put(ThemeConstants.AC_1, "#FFFF00");
        defaultColorMap.put(ThemeConstants.AC_2, "#ffdd00");
        defaultColorMap.put(ThemeConstants.BACK_DARK, "#282828");
        defaultColorMap.put(ThemeConstants.BACK_DARK_2, "#424242");
        defaultColorMap.put(ThemeConstants.BACK_DARK_LIGHT, "#D7D7D7");
        defaultColorMap.put(ThemeConstants.DEEP_DARK, "#1E1E1E");
        defaultColorMap.put(ThemeConstants.BACK_IC_COLOR, "#696969");
        defaultColorMap.put(ThemeConstants.ERROR_COLOR, "#FF0000");
        defaultColorMap.put(ThemeConstants.HINT, "#C8C8C8");

        defaultColorMap.put(ThemeConstants.TEXT_LIGHT, "#282828");
        defaultColorMap.put(ThemeConstants.TEXT_DARK, "#FCFCFC");
    }

    public Map<String, String> getColorMap() {
        return defaultColorMap;
    }

    public Map<String, Integer> getIntegerColorMap() {
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.clear();

        for(Map.Entry<String, String> entry : getColorMap().entrySet())
            integerMap.put(entry.getKey(), Color.parseColor(entry.getValue()));

        return integerMap;
    }
}