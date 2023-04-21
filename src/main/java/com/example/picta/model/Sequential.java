package com.example.picta.model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Sequential implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String imagePath;
    private ArrayList<Pictogram> pictoList;


    public Sequential(UUID id, String name, String des){
        this.id = id;
        this.name = name;
        this.description = des;
        this.pictoList = new ArrayList<>();
        this.imagePath = "src/main/resources/com/example/picta/default.png";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addPictogram(Pictogram picto){
        pictoList.add(picto);
    }

    public ArrayList<Pictogram> getPictoList(){
        return pictoList;
    }

    public UUID getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }
}
