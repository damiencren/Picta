package fr.damiencren.picta.model;

public class Pictogram implements java.io.Serializable{
    private String id;
    private String url;

    public Pictogram(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getID() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Pictogram{" +
                "id=" + id +
                ", url='" + url +
                '}';
    }
}