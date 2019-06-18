package com.mobile.kiril.tagnote.spotRowBuilder;

public class SpotRowBuilderMenu {
    private int spotRowWeight, rowAmount;
    private float menuYSize, menuXSize, spotA, density, spotBoxPadding, spotBoxMargin, spotBoxWidth, boxWidth, boxHeight, spotRowWeightF, rowAmountF;

    public SpotRowBuilderMenu(float menuYSize, float menuXSize, float density) {
        this.menuYSize = menuYSize;
        this.menuXSize = menuXSize;
        this.density = density;

        spotBoxPadding = (int)dpToPixel(6);

        if (menuYSize > menuXSize) {
            spotRowWeight = 5;
            spotRowWeightF = 5;
            spotBoxMargin = dpToPixel(14);
            rowAmount = 4;
            rowAmountF = 4;
        } else {
            spotRowWeight = 10;
            spotRowWeightF = 10;
            spotBoxMargin = dpToPixel(34);
            rowAmount = 2;
            rowAmountF = 2;
        }

        spotBoxWidth = menuXSize - (spotBoxMargin*2);
        float insideWidth = spotBoxWidth - (spotBoxPadding*2);
        spotA = insideWidth/spotRowWeightF;

        //forBoxSize
        boxWidth = spotBoxWidth;
        if (menuYSize > menuXSize) boxHeight = spotA*4;
        else boxHeight = spotA*2;
        boxHeight += (spotBoxPadding*2);
    }

    private float dpToPixel(float dp) {
        return dp * density;
    }

    public float getSpotBoxPadding() {
        return spotBoxPadding;
    }

    public float getSpotBoxMargin() {
        return spotBoxMargin;
    }

    public float getSpotA() {
        return spotA;
    }

    public float getBoxWidth() {
        return boxWidth;
    }

    public float getBoxHeight() {
        return boxHeight;
    }

    public float getSpotBoxWidth() {
        return spotBoxWidth;
    }

    public float getDensity() {
        return density;
    }

    public int getSpotRowWeight() {
        return spotRowWeight;
    }

    public int getRowAmount() {
        return rowAmount;
    }
}
