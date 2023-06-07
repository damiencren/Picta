package fr.damiencren.picta.model;

import javafx.scene.image.Image;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Sequential implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private byte[] image;
    private SerializableColor seriColor;
    private ArrayList<Pictogram> pictoList;

    public Sequential(UUID id, String name, String des, byte[] image, SerializableColor seriColor){
        this.id = id;
        this.name = name;
        this.description = des;
        this.image = image;
        this.seriColor = seriColor;
        this.pictoList = new ArrayList<>();
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

    public byte[] getImage() {
        return image;
    }
}
