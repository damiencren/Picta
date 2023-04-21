package com.example.picta.controller;

import com.example.picta.MainApplication;
import com.example.picta.model.Sequential;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CreationController implements Initializable {
    private MainApplication mainApplication;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private ImageView previewImageView;

    @FXML
    void onCancelBtnClicked(ActionEvent event) {
        mainApplication.getSecondStage().close();
    }

    @FXML
    void onCreateBtnClicked(ActionEvent event) {
        mainApplication.getHelloController().addSequential(new Sequential(UUID.randomUUID(),nameTextField.getText(), descriptionTextField.getText()));
        mainApplication.getSecondStage().close();
        mainApplication.getHelloController().refresh();
    }

    @FXML
    void onFileBrowserBtnClicked(ActionEvent event) {

    }
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
