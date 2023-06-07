package fr.damiencren.picta.controller;

import fr.damiencren.picta.MainApplication;
import fr.damiencren.picta.model.BinaryWriter;
import fr.damiencren.picta.model.Sequential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private Button duplicateBtn;
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
            Image image = new Image(new ByteArrayInputStream(seq.getImage()));
            ImageView view = new ImageView(image);
            errorLabel.setVisible(false);

            view.setFitHeight(190);
            view.setPreserveRatio(true);

            Button btn = new Button(seq.getName(), view);
            btn.setStyle("-fx-font-size: 15px;");
            btn.setPrefSize(240,240);
            btn.setGraphic(view);
            btn.setContentDisplay(ContentDisplay.TOP);
            btn.setUserData(seq.getId());
            btn.setOnMouseClicked(this::onClick);
            mainTilePane.getChildren().add(btn);
        }
        editBtn.setDisable(mainTilePane.getChildren().size()==0||SelectedButton==null);
        
        Button Addbtn = new Button("+");
        Addbtn.setStyle("-fx-font-size: 50px;");
        Addbtn.setPrefSize(240,240);
        Addbtn.setContentDisplay(ContentDisplay.TOP);
        Addbtn.setOnMouseClicked(this::onAddBtnClicked);
        mainTilePane.getChildren().add(Addbtn);
    }

    private void onAddBtnClicked(MouseEvent mouseEvent) {
        mainApplication.showCreationScene();
    }


    @FXML
    void onDuplicateBtnClicked(ActionEvent event) {
        if (SelectedButton != null){
            UUID id = (UUID) SelectedButton.getUserData();
            for (Sequential seq :seqList) {
                if (seq.getId().equals(id)){
                    UUID new_id = UUID.randomUUID();
                    Sequential newSeq = new Sequential(new_id,seq.getName(), seq.getDescription(), seq.getImage(), seq.getColor());
                    seqList.add(newSeq);
                    break;
                }
            }
            refresh();
        }
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
        if (SelectedButton != null){
            for (Sequential seq :seqList) {
                if (seq.getId().equals(SelectedButton.getUserData())){
                    mainApplication.showEditionScene(seq);
                    break;
                }
            }
        } else {
            errorLabel.setVisible(true);
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        Alert alertQuit = new Alert(Alert.AlertType.CONFIRMATION);
        alertQuit.setTitle("Quitter");
        alertQuit.setHeaderText("Voulez vous vraiment quitter Picta ?");

        Optional<ButtonType> option = alertQuit.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            System.exit(1);
        }
    }


    public void onCreateBtnClicked(ActionEvent actionEvent) {
        mainApplication.showCreationScene();
    }

    @FXML
    void createSequentialByFile(ActionEvent event) {
        mainApplication.showCreationScene();
    }

    @FXML
    void editSequentialByFile(ActionEvent event) {
        if (SelectedButton != null){
            for (Sequential seq :seqList) {
                if (seq.getId().equals(SelectedButton.getUserData())){
                    mainApplication.showEditionScene(seq);
                    break;
                }
            }
        } else {
            errorLabel.setVisible(true);
        }
    }


}


