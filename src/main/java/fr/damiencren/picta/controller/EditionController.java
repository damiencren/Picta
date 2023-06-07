package fr.damiencren.picta.controller;

import fr.damiencren.picta.MainApplication;
import fr.damiencren.picta.model.Pictogram;
import fr.damiencren.picta.model.PictogramManager;
import fr.damiencren.picta.model.Sequential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditionController implements Initializable {

    private Sequential seq;

    @FXML
    private TilePane resultTilePane;

    @FXML
    private TextField limitTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private TilePane seqTilePane;
    private MainApplication mainApplication;

    @FXML
    void onSearchByNameBtnClicked(ActionEvent event) {
        String search = searchTextField.getText();
        Integer limit = Integer.valueOf(limitTextField.getText());
        ArrayList<Pictogram> resultList = PictogramManager.getAllPictograms();
        ArrayList<Pictogram> pictoList = new ArrayList<>();
        for (int i=0; i<limit; i++){
            pictoList.add(resultList.get(i));
        }

        resultTilePane.getChildren().clear();
        for (Pictogram picto : pictoList){
            ImageView view = new ImageView(picto.getUrl());
            resultTilePane.getChildren().add(view);
        }
    }

    public void setSequential(Sequential seq){
        this.seq = seq;
    }
    public void refresh(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
}
