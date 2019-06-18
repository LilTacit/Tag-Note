package com.mobile.kiril.tagnote.entities;

public class Category {
    private int id;
    private String name;
    private String spot;
    private int noteAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public int getNoteAmount() {
        return noteAmount;
    }

    public void setNoteAmount(int noteAmount) {
        this.noteAmount = noteAmount;
    }
}
