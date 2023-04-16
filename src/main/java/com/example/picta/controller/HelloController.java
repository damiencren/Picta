package com.example.picta.controller;

import com.example.picta.model.Sequential;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private ArrayList<Sequential> seqList = new ArrayList<Sequential>();
    Sequential seq = new Sequential("aa","aa");

    @FXML
    private TilePane mainTilePane;


    @FXML
    private TextField mainSearchBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //test
        String[] cars = {"Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW", "Ford","Volvo", "BMW",};
        for (String name : cars) {
            seqList.add(new Sequential(name, "aaa"));
        }


        for (Sequential seq : seqList){


            FileInputStream inputstream = null;
            try {
                inputstream = new FileInputStream("src/main/resources/com/example/picta/image.jpg");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image img = new Image(inputstream);
            ImageView view = new ImageView(img);

            view.setFitHeight(200);
            view.setPreserveRatio(true);

            Button btn = new Button(seq.getName(), view);
            btn.setPrefSize(240,240);
            btn.setGraphic(view);
            btn.setContentDisplay(ContentDisplay.TOP);

            mainTilePane.getChildren().add(btn);

        }
    }
}