package fr.iclipse.picta.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class PictogramManager {
    static final ObjectMapper objectMapper = new ObjectMapper();

    public static ArrayList<Pictogram> getAllPictograms() {
        URL url;
        JsonNode jsonNode;
        try {
            url = new URL("https://api.arasaac.org/api/pictograms/all/fr");
            jsonNode = objectMapper.readTree(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getPictograms(jsonNode);
    }

    public static ArrayList<Pictogram> getPictogramsByKeyword(String keyword) {
        try {
            URL url = new URL("https://api.arasaac.org/api/pictograms/fr/search/" + keyword);
            JsonNode jsonNode = objectMapper.readTree(url);
            return getPictograms(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Pictogram> getPictograms(JsonNode jsonNode) {
        ArrayList<Pictogram> pictograms = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            String id = node.get("_id").asText();
            pictograms.add(new Pictogram(id, "https://api.arasaac.org/api/pictograms/" + id));
        }
        return pictograms;
    }

    public static Pictogram getPictogramById(String id) {
        return new Pictogram(id, "https://api.arasaac.org/api/pictograms/" + id);
    }
}

