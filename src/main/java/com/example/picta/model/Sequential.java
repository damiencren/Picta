package com.example.picta.model;

import javafx.scene.paint.Color;

public class Sequential {
    private String name;
    private String description;

    public Sequential(String name, String des){
        this.name = name;
        this.description = des;
    }

    public String getName() {
        return name;
    }
}
