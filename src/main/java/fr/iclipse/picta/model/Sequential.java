package fr.iclipse.picta.model;

import javafx.scene.paint.Color;


import java.io.Serializable;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sequential implements Serializable {
    private final UUID id;
    private String name;
    private String description;
    private final byte[] image;
    private SerializableColor seriColor;
    private final ConcurrentLinkedQueue<Pictogram> pictoList;

    public Sequential(UUID id, String name, String des, byte[] image, SerializableColor seriColor){
        this.id = id;
        this.name = name;
        this.description = des;
        this.image = image;
        this.seriColor = seriColor;
        this.pictoList = new ConcurrentLinkedQueue<>();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addPictogram(Pictogram pictogram){
        this.pictoList.add(pictogram);
    }

    public ConcurrentLinkedQueue<Pictogram> getPictoList(){
        return this.pictoList;
    }

    public UUID getId() {
        return this.id;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void removeById(String id){
        this.pictoList.removeIf(pictogram -> pictogram.getID().equals(id));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(Color color){
        this.seriColor = new SerializableColor(color);
    }

    public SerializableColor getColor() {
        return this.seriColor;
    }

    public void switchElements(int index1, int index2) {
        Pictogram[] elements = new Pictogram[this.pictoList.size()];
        this.pictoList.toArray(elements);

        Pictogram temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;

        this.pictoList.clear();
        Collections.addAll(this.pictoList, elements);
    }
}
