package com.mobile.kiril.tagnote;

import java.util.ArrayList;
import java.util.List;

public class SpotManager {
    private List<String> colorIcList;

    public SpotManager() {
        colorIcList = new ArrayList<>();

        colorIcList.add("spot_clean.png");
        colorIcList.add("spot_color_red.png");
        colorIcList.add("spot_color_blue.png");
        colorIcList.add("spot_color_green.png");
        colorIcList.add("spot_color_yellow.png");
        colorIcList.add("spot_color_orange.png");
        colorIcList.add("spot_color_pink.png");
        colorIcList.add("spot_color_purple.png");
        colorIcList.add("spot_color_dark_blue.png");
        colorIcList.add("spot_color_dark_green.png");
        colorIcList.add("spot_color_gray.png");
        colorIcList.add("spot_color_black.png");
    }

    public List<String> getColorIcList() {
        return colorIcList;
    }
}
