package fr.iclipse.picta.model;

public class Pictogram implements java.io.Serializable{
    private final String id, url;

    public Pictogram(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getID() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return "Pictogram{" +
                "id=" + this.id +
                ", url='" + this.url +
                '}';
    }
}