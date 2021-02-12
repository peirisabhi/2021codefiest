package com.zonebecreations.a2021codefiest.pojo;

public class MapDistance {
    String text;
    double value;

    public MapDistance(String text, double value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
