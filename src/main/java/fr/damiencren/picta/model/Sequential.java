package fr.damiencren.picta.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;


import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sequential implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private byte[] image;
    private SerializableColor seriColor;
    private ConcurrentLinkedQueue<Pictogram> pictoList;

    public Sequential(UUID id, String name, String des, byte[] image, SerializableColor seriColor){
        this.id = id;
        this.name = name;
        this.description = des;
        this.image = image;
        this.seriColor = seriColor;
        this.pictoList = new ConcurrentLinkedQueue<>();
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

    public ConcurrentLinkedQueue<Pictogram> getPictoList(){
        return pictoList;
    }

    public UUID getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void delWithId(String id){
        for (Pictogram picto : pictoList){
            if (picto.getID().equals(id)){
                pictoList.remove(picto);
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(Color color){
        seriColor = new SerializableColor(color);
    }
}
