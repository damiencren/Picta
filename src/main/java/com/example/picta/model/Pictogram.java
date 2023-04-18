package com.example.picta.model;

class Pictogram {
    public String id;
    public String url;

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