package com.mobile.kiril.tagnote.themes;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ThemeSetter {
    private List<ThemeViewWrap> wrapsList;
    private Map<String, Integer> colorsMap;

    public ThemeSetter(List<ThemeViewWrap> wrapsList, Map<String, Integer> colorsMap) {
        this.wrapsList = wrapsList;
        this.colorsMap = colorsMap;
    }

    public void startThemeSet() {
        for(ThemeViewWrap wrap : wrapsList) {
            View v = wrap.view;
            int color = colorsMap.get(wrap.color);

            if(wrap.type == ThemeWrapType.CLASSIC_VIEW) {
                v.setBackgroundColor(color);
            } else if (wrap.type == ThemeWrapType.TEXT_VIEW) {
                ((TextView) v).setTextColor(color);
            } else if (wrap.type == ThemeWrapType.BUTTON_TEXT) {
                ((Button) v).setTextColor(color);
            } else if (wrap.type == ThemeWrapType.ICON_VIEW) {
                v.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } else if (wrap.type == ThemeWrapType.SHAPE_BOX) {
                GradientDrawable shape = (GradientDrawable) v.getBackground();
                shape.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                v.setBackground(shape);
            } else if (wrap.type == ThemeWrapType.RIPPLE_BOX) {
                RippleDrawable ripple = (RippleDrawable) v.getBackground();
                ripple.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                v.setBackground(ripple);
            } else if (wrap.type == ThemeWrapType.EDITTEXT_TEXT) {
                ((EditText)v).setTextColor(color);
            } else if (wrap.type == ThemeWrapType.EDITTEXT_HINT) {
                ((EditText)v).setHintTextColor(color);
            }
        }
    }
}
