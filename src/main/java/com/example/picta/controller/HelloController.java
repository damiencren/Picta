package com.example.picta.controller;

import com.example.picta.MainApplication;
import com.example.picta.model.BinaryWriter;
import com.example.picta.model.Sequential;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class HelloController implements Initializable {
    private Button SelectedButton = null;
    private ArrayList<Sequential> seqList = new ArrayList<>();
    private MainApplication mainApplication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ArrayList<Sequential> seqListTest = BinaryWriter.readObject();

            BinaryWriter.writeObject(seqListTest);

            seqList = BinaryWriter.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        refresh();
    }

    public void refresh(){
        mainTilePane.getChildren().clear();
        for (Sequential seq : seqList){

            InputStream inputstream = null;
            try {
                inputstream = new FileInputStream(seq.getImagePath()); //In(seq.getImagePath()).openStream();
            } catch (IOException e) {
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
            btn.setUserData(seq.getId());
            btn.setOnMouseClicked(this::onClick);
            mainTilePane.getChildren().add(btn);
        }
        editBtn.setDisable(mainTilePane.getChildren().size()==0||SelectedButton==null);
    }


    @FXML
    void onClick(MouseEvent event) {
        if (event.getSource() instanceof Button && ((Button) event.getSource()).getUserData()!=null) {
            SelectedButton = (Button) event.getSource();
            System.out.println("Un bouton a été cliqué");
            editBtn.setDisable(false);
        } else {
            System.out.println("Un autre a été cliqué");
            SelectedButton = null;
            editBtn.setDisable(true);
        }
    }


    @FXML
    private TilePane mainTilePane;


    @FXML
    private Button editBtn;


    @FXML
    private TextField mainSearchBar;

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public interface SceneChangeCallback {
        void onSceneChange(String fxmlPath);
    }

    private SceneChangeCallback callback;

    public void setSceneChangeCallback(SceneChangeCallback callback) {
        this.callback = callback;
    }

    public void onDeleteBtnClicked(ActionEvent actionEvent) {


        if (SelectedButton != null){
            UUID id = (UUID) SelectedButton.getUserData();
            for (Sequential seq :seqList) {
                if (seq.getId().equals(id)){
                    seqList.remove(seq);
                    break;
                }
            }
            mainTilePane.getChildren().remove(SelectedButton);
            try {
                BinaryWriter.writeObject(seqList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        refresh();
    }

    public void addSequential(Sequential seq){
        seqList.add(seq);
        try {
            BinaryWriter.writeObject(seqList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onEditBtnClicked(ActionEvent actionEvent) {

    }

    public void onCreateBtnClicked(ActionEvent actionEvent) {
        mainApplication.showSecondScene();
    }



}


