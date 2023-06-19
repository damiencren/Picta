package fr.iclipse.picta;

import fr.iclipse.picta.controller.CreateController;
import fr.iclipse.picta.controller.EditController;
import fr.iclipse.picta.controller.MainController;
import fr.iclipse.picta.model.Sequential;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Picta extends Application {

    private Stage stage;

    private Stage secondStage;
    private Scene firstScene;
    private Scene secondScene;

    private Scene editionScene;

    private MainController helloController;
    private CreateController createController;
    private EditController editController;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException{

        this.stage = stage;
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("book.png"))));
        FXMLLoader firstFxmlLoader = new FXMLLoader(Picta.class.getResource("main-view.fxml"));
        Scene firstScene = new Scene(firstFxmlLoader.load(),1280,720);
        this.helloController = firstFxmlLoader.getController();
        this.helloController.setMainApplication(this);
        firstScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.firstScene = firstScene;

        FXMLLoader secondFxmlLoader = new FXMLLoader(Picta.class.getResource("creation-view.fxml"));
        Scene secondScene = new Scene(secondFxmlLoader.load(), 350, 500);
        CreateController createController = secondFxmlLoader.getController();
        this.createController = createController;
        createController.setMainApplication(this);
        secondScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.secondScene = secondScene;

        FXMLLoader editionFxmlLoader = new FXMLLoader(Picta.class.getResource("edition-view.fxml"));
        Scene editionScene  = new Scene(editionFxmlLoader.load(),1280, 720);
        EditController editController = editionFxmlLoader.getController();
        this.editController = editController;
        editController.setMainApplication(this);
        editionScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm());
        this.editionScene = editionScene;

        this.secondStage =  new Stage();
        stage.setMaximized(true);
        stage.setTitle("Picta");
        stage.setScene(firstScene);
        stage.show();
        this.secondStage.initModality(Modality.APPLICATION_MODAL);
        this.secondStage.setResizable(false);

        this.secondStage.setOnCloseRequest(event -> this.createController.refresh());
    }

    public void showCreationScene() {
        this.secondStage.setTitle("Création");
        this.secondStage.setScene(this.secondScene);
        this.secondStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("plus.png"))));
        this.secondStage.show();
    }

    public void showEditionScene(Sequential sequential) {
        this.stage.setTitle("Edition");
        this.stage.setScene(this.editionScene);
        this.stage.setMaximized(false);
        this.stage.setMaximized(true);
        this.stage.setResizable(false);
        this.editController.setSequential(sequential);
        this.stage.show();
    }

    public Stage getSecondStage() {
        return this.secondStage;
    }

    public MainController getHelloController() {
        return this.helloController;
    }

    public void showMainView() {
        this.stage.setTitle("Picta");
        this.stage.setScene(this.firstScene);
        this.stage.setMaximized(false);
        this.stage.setMaximized(true);
        this.stage.show();
    }
}

