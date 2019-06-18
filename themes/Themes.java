package com.mobile.kiril.tagnote.themes;

import android.content.Context;

import com.mobile.kiril.tagnote.activeThemes.*;

import java.util.HashMap;
import java.util.Map;

public class Themes {
    private Map<String, Theme> themes;
    private Context context;
    public static String DEFAULT = "theme_default";
    public static String DEFAULTS = "Defaults";
    public static String TESTS = "Tests";
    public static String COMMONS = "Commons";
    public static String JUCIES = "Jucies";
    public static String DARKS = "Darks";
    public static String MINIMALISTICS = "Minimalistics";

    public Themes(Context context) {
        this.context = context;

        ThemeDefaultMock defTheme = new ThemeDefaultMock();
        ThemeTestMock testTheme = new ThemeTestMock();
        ThemeTestDark testDarkTheme = new ThemeTestDark();

        themes = new HashMap<>();
        if(themes.size() > 0) themes.clear();

        themes.put(DEFAULT, new Theme("Default theme", DEFAULT, "collection_default", DEFAULTS, "0", defTheme.getColorMap(), defTheme.getIntegerColorMap(), true));

        //dark
        ThemeDarkYellow darkYellow = new ThemeDarkYellow();
        ThemeDarkRed darkRed = new ThemeDarkRed();
        ThemeDarkGreen darkGreen = new ThemeDarkGreen();
        ThemeDarkBlue darkBlue = new ThemeDarkBlue();
        ThemeDarkPink darkPink = new ThemeDarkPink();
        ThemeDarkPurple darkPurple = new ThemeDarkPurple();
        ThemeDarkOrange darkOrange = new ThemeDarkOrange();
        themes.put("theme_dark_yellow", new Theme("Common Red", "theme_dark_yellow", "collection_dark", DARKS, "", darkYellow.getColorMap(), darkYellow.getIntegerColorMap(), false));
        themes.put("theme_dark_red", new Theme("Common Red", "theme_dark_red", "collection_dark", DARKS, "", darkRed.getColorMap(), darkRed.getIntegerColorMap(), false));
        themes.put("theme_dark_green", new Theme("Common Green", "theme_dark_green", "collection_dark", DARKS, "", darkGreen.getColorMap(), darkGreen.getIntegerColorMap(), false));
        themes.put("theme_dark_blue", new Theme("Common Blue", "theme_dark_blue", "collection_dark", DARKS, "", darkBlue.getColorMap(), darkBlue.getIntegerColorMap(), false));
        themes.put("theme_dark_pink", new Theme("Common Pink", "theme_dark_pink", "collection_dark", DARKS, "", darkPink.getColorMap(), darkPink.getIntegerColorMap(), false));
        themes.put("theme_dark_orange", new Theme("Common Orange", "theme_dark_orange", "collection_dark", DARKS, "", darkOrange.getColorMap(), darkOrange.getIntegerColorMap(), false));
        themes.put("theme_dark_purple", new Theme("Common Purple", "theme_dark_purple", "collection_dark", DARKS, "", darkPurple.getColorMap(), darkPurple.getIntegerColorMap(), false));

        //minimal
        ThemeMinimalisticBW minimalisticBW = new ThemeMinimalisticBW();
        ThemeMinimalisticSilver minimalisticSilver = new ThemeMinimalisticSilver();
        ThemeMinimalisticOnlyDark minimalisticOnlyDark = new ThemeMinimalisticOnlyDark();
        ThemeMinimalisticOld minimalisticOld = new ThemeMinimalisticOld();
        ThemeMinimalisticUltramarine minimalisticUltramarine = new ThemeMinimalisticUltramarine();
        ThemeMinimalisticBrown minimalisticBrown = new ThemeMinimalisticBrown();
        themes.put("theme_minimalistic_bw", new Theme("Minimalistic Black & White", "theme_minimalistic_bw", "collection_minimalistic", MINIMALISTICS, "", minimalisticBW.getColorMap(), minimalisticBW.getIntegerColorMap(), false));
        themes.put("theme_minimalistic_silver", new Theme("Minimalistic Silver", "theme_minimalistic_silver", "collection_minimalistic", MINIMALISTICS, "", minimalisticSilver.getColorMap(), minimalisticSilver.getIntegerColorMap(), false));
        themes.put("theme_minimalistic_only_dark", new Theme("Minimalistic Only-Dark", "theme_minimalistic_only_dark", "collection_minimalistic", MINIMALISTICS, "", minimalisticOnlyDark.getColorMap(), minimalisticOnlyDark.getIntegerColorMap(), false));
        themes.put("theme_minimalistic_old", new Theme("Minimalistic Old", "theme_minimalistic_old", "collection_minimalistic", MINIMALISTICS, "", minimalisticOld.getColorMap(), minimalisticOld.getIntegerColorMap(), false));
        themes.put("theme_minimalistic_ultramarine", new Theme("Minimalistic Ultramarine", "theme_minimalistic_ultramarine", "collection_minimalistic", MINIMALISTICS, "", minimalisticUltramarine.getColorMap(), minimalisticUltramarine.getIntegerColorMap(), false));
        themes.put("theme_minimalistic_brown", new Theme("Minimalistic Brown", "theme_minimalistic_brown", "collection_minimalistic", MINIMALISTICS, "", minimalisticBrown.getColorMap(), minimalisticBrown.getIntegerColorMap(), false));

        //common
        ThemeCommonRed commonRed = new ThemeCommonRed();
        ThemeCommonGreen commonGreen = new ThemeCommonGreen();
        ThemeCommonBlue commonBlue = new ThemeCommonBlue();
        ThemeCommonPink commonPink = new ThemeCommonPink();
        ThemeCommonPurple commonPurple = new ThemeCommonPurple();
        ThemeCommonOrange commonOrange = new ThemeCommonOrange();
        themes.put("theme_common_red", new Theme("Common Red", "theme_common_red", "collection_common", COMMONS, "", commonRed.getColorMap(), commonRed.getIntegerColorMap(), false));
        themes.put("theme_common_green", new Theme("Common Green", "theme_common_green", "collection_common", COMMONS, "", commonGreen.getColorMap(), commonGreen.getIntegerColorMap(), false));
        themes.put("theme_common_blue", new Theme("Common Blue", "theme_common_blue", "collection_common", COMMONS, "", commonBlue.getColorMap(), commonBlue.getIntegerColorMap(), false));
        themes.put("theme_common_pink", new Theme("Common Pink", "theme_common_pink", "collection_common", COMMONS, "", commonPink.getColorMap(), commonPink.getIntegerColorMap(), false));
        themes.put("theme_common_purple", new Theme("Common Purple", "theme_common_purple", "collection_common", COMMONS, "", commonPurple.getColorMap(), commonPurple.getIntegerColorMap(), false));
        themes.put("theme_common_orange", new Theme("Common Orange", "theme_common_orange", "collection_common", COMMONS, "", commonOrange.getColorMap(), commonOrange.getIntegerColorMap(), false));

        //juicy
        ThemeJuicyRed juicyRed = new ThemeJuicyRed();
        ThemeJuicyGreen juicyGreen = new ThemeJuicyGreen();
        ThemeJuicyBlue juicyBlue = new ThemeJuicyBlue();
        ThemeJuicyPink juicyPink = new ThemeJuicyPink();
        ThemeJuicyPinkPurple juicyPinkPurple = new ThemeJuicyPinkPurple();
        ThemeJuicyOrangeBlue juicyOrangeBlue = new ThemeJuicyOrangeBlue();
        themes.put("theme_juicy_red", new Theme("Juicy Red", "theme_juicy_red", "collection_juicy", JUCIES, "", juicyRed.getColorMap(), juicyRed.getIntegerColorMap(), false));
        themes.put("theme_juicy_green", new Theme("Juicy Green", "theme_juicy_green", "collection_juicy", JUCIES, "", juicyGreen.getColorMap(), juicyGreen.getIntegerColorMap(), false));
        themes.put("theme_juicy_blue", new Theme("Juicy Blue", "theme_juicy_blue", "collection_juicy", JUCIES, "", juicyBlue.getColorMap(), juicyBlue.getIntegerColorMap(), false));
        themes.put("theme_juicy_pink", new Theme("Juicy Pink", "theme_juicy_pink", "collection_juicy", JUCIES, "", juicyPink.getColorMap(), juicyPink.getIntegerColorMap(), false));
        themes.put("theme_juicy_pink_purple", new Theme("Juicy Pink-Purple", "theme_juicy_pink_purple", "collection_juicy", JUCIES, "", juicyPinkPurple.getColorMap(), juicyPinkPurple.getIntegerColorMap(), false));
        themes.put("theme_juicy_orange_blue", new Theme("Juicy Orange-Blue", "theme_juicy_orange_blue", "collection_juicy", JUCIES, "", juicyOrangeBlue.getColorMap(), juicyOrangeBlue.getIntegerColorMap(), false));
    }

    public Map<String, Theme> getThemes() {
        return themes;
    }
}
