package com.mobile.kiril.tagnote.themes;

public class Collection {
    private String id, name, shortName, price;
    private boolean isPurchased;

    public Collection(String id, String name, String shortName, String price) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.price = price;
        isPurchased = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return isPurchased;
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
