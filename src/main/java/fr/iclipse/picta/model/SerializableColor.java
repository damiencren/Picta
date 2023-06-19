package fr.iclipse.picta.model;

import java.io.Serializable;
import javafx.scene.paint.Color;


public class SerializableColor implements Serializable
{
    private final double red, green, blue, alpha;

    public SerializableColor(Color color)
    {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
    }
    public SerializableColor(double red, double green, double blue, double alpha)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public String getRGBA()
    {
       return String.format("rgba(%d, %d, %d, %d)", (int)red*255, (int)green*255, (int)blue*255, (int)alpha);
    }
}