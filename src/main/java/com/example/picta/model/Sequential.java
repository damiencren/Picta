package com.example.picta.model;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Sequential {
    private String name;
    private String description;
    private ArrayList<Pictogram> pictoList;


    public Sequential(String name, String des, ArrayList<Pictogram> pictoList){
        this.name = name;
        this.description = des;
        this.pictoList = pictoList;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addPictogram(String url){
        pictoList.add(new Pictogram(url));
    }
}
