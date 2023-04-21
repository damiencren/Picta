package com.example.picta;

import com.example.picta.controller.CreationController;
import com.example.picta.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    private Stage stage;
    private Stage secondStage;
    private Scene firstScene;
    private Scene secondScene;

    private HelloController helloController;

    @Override
    public void start(Stage stage) throws IOException{
        this.stage = stage;
        FXMLLoader firstFxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene firstScene = new Scene(firstFxmlLoader.load(),1280,720);
        this.helloController = firstFxmlLoader.getController();
        helloController.setMainApplication(this);
        firstScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.firstScene = firstScene;

        FXMLLoader secondFxmlLoader = new FXMLLoader(MainApplication.class.getResource("creation-view.fxml"));
        Scene secondScene = new Scene(secondFxmlLoader.load(), 300, 500);
        CreationController controller2 = secondFxmlLoader.getController();
        controller2.setMainApplication(this);
        secondScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.secondScene = secondScene;

        stage.setMaximized(true);
        stage.setTitle("Picta");
        stage.setScene(firstScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showSecondScene() {
        Stage secondStage = new Stage();
        secondStage.setTitle("Creation");
        secondStage.setScene(secondScene);
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setResizable(false);
        secondStage.show();
        this.secondStage = secondStage;
    }

    public Stage getStage() {
        return stage;
    }
    public Stage getSecondStage() {
        return secondStage;
    }

    public HelloController getHelloController() {
        return helloController;
    }
}

