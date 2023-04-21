package com.example.picta.model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Sequential implements Serializable {

    final private static String DEFAULT_IMAGE_PATH = "src/main/resources/com/example/picta/default.png";
    private UUID id;
    private String name;
    private String description;
    private String imagePath;
    private ArrayList<Pictogram> pictoList;

    public Sequential(UUID id, String name, String des, String imagePath){
        this.id = id;
        this.name = name;
        this.description = des;
        this.imagePath = imagePath;
        this.pictoList = new ArrayList<>();
    }

    public Sequential(UUID id, String name, String des){
        this(id, name, des, DEFAULT_IMAGE_PATH);
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
