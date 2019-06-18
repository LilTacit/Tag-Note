package com.mobile.kiril.tagnote.themes;

import java.util.Map;

public class Theme {
    private String name, id, collectionId, collection, price;
    private Map<String, Integer> colorMap;
    private Map<String, String> colorRow;
    private boolean isPurchased;

    public Theme(String name, String id, String collectionId, String collection, String price, Map<String, String> colorRow, Map<String, Integer> colorMap, boolean isPurchased) {
        this.name = name;
        this.price = price;
        this.colorMap = colorMap;
        this.isPurchased = isPurchased;
        this.colorRow = colorRow;
        this.id = id;
        this.collection = collection;
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Map<String, Integer> getColorMap() {
        return colorMap;
    }

    public Map<String, String> getColorRow() {
        return colorRow;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public String getId() {
        return id;
    }

    public String getCollection() {
        return collection;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
