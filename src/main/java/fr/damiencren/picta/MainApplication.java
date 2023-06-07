package fr.damiencren.picta;

import fr.damiencren.picta.controller.CreationController;
import fr.damiencren.picta.controller.EditionController;
import fr.damiencren.picta.controller.MainController;
import fr.damiencren.picta.model.Sequential;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MainApplication extends Application {

    private Stage stage;

    private Stage secondStage;
    private Scene firstScene;
    private Scene secondScene;

    private Scene editionScene;

    private MainController helloController;
    private CreationController creationController;
    private EditionController editionController;


    @Override
    public void start(Stage stage) throws IOException{

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        this.stage = stage;
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("book.png"))));
        FXMLLoader firstFxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene firstScene = new Scene(firstFxmlLoader.load(),1280,720);
        this.helloController = firstFxmlLoader.getController();
        helloController.setMainApplication(this);
        firstScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.firstScene = firstScene;

        FXMLLoader secondFxmlLoader = new FXMLLoader(MainApplication.class.getResource("creation-view.fxml"));
        Scene secondScene = new Scene(secondFxmlLoader.load(), 350, 460);
        CreationController creationController = secondFxmlLoader.getController();
        this.creationController = creationController;
        creationController.setMainApplication(this);
        secondScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.secondScene = secondScene;

        FXMLLoader editionFxmlLoader = new FXMLLoader(MainApplication.class.getResource("edition-view.fxml"));
        Scene editionScene  = new Scene(editionFxmlLoader.load(),screenWidth,screenHeight);
        EditionController editionController = editionFxmlLoader.getController();
        this.editionController = editionController;
        editionController.setMainApplication(this);
        editionScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.editionScene = editionScene;

        this.secondStage =  new Stage();
        stage.setMaximized(true);
        stage.setTitle("Picta");
        stage.setScene(firstScene);
        stage.show();
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setResizable(false);

        secondStage.setOnCloseRequest(event -> {
            this.creationController.refresh();
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void showCreationScene() {
        secondStage.setTitle("Creation");
        secondStage.setScene(secondScene);
        secondStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("plus.png"))));
        secondStage.show();
    }

    public void showEditionScene(Sequential seq) {
        stage.setTitle("Edition");
        stage.setScene(editionScene);
        stage.setMaximized(false);
        stage.setMaximized(true);
        editionController.setSequential(seq);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }
    public Stage getSecondStage() {
        return secondStage;
    }

    public MainController getHelloController() {
        return helloController;
    }

}

