package com.example.picta.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PictogramManager {
    static final ObjectMapper objectMapper = new ObjectMapper();


    public static ArrayList<Pictogram> getAllPictograms() throws IOException {
        URL url = new URL("https://api.arasaac.org/api/pictograms/all/fr");
        JsonNode jsonNode = objectMapper.readTree(url);
        ArrayList<Pictogram> pictograms = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            String id = node.get("_id").asText();
            Pictogram pictogram = new Pictogram(id, "https://api.arasaac.org/api/pictograms/" + id);
            pictograms.add(pictogram);
        }
        return pictograms;
    }

    public static ArrayList<Pictogram> getPictogramsByKeyword(String keyword) throws IOException {
        URL url = new URL("https://api.arasaac.org/api/pictograms/fr/search/" + keyword);
        JsonNode jsonNode = objectMapper.readTree(url);
        ArrayList<Pictogram> pictograms = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            String id = node.get("_id").asText();
            Pictogram pictogram = new Pictogram(id, "https://api.arasaac.org/api/pictograms/" + id);
            pictograms.add(pictogram);
        }
        return pictograms;
    }

    public static ArrayList<String> getKeywords() throws IOException {
        URL url = new URL("https://api.arasaac.org/api/keywords/fr");
        JsonNode jsonNode = objectMapper.readTree(url);
        JsonNode dataNode = jsonNode.get("words");
        ArrayList<String> keywords = new ArrayList<>();
        for (JsonNode node : dataNode) {
            String keyword = node.asText();
            keywords.add(keyword);
        }
        return keywords;
    }
}

