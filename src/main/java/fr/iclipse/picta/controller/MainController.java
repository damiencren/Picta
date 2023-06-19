package fr.iclipse.picta.controller;

import fr.iclipse.picta.Picta;
import fr.iclipse.picta.utils.BinaryWriter;
import fr.iclipse.picta.model.Sequential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private Button duplicateBtn;
    private Button selectedButton;
    private ArrayList<Sequential> seqList;
    private Picta picta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.seqList = new ArrayList<>();
        try {
            BinaryWriter.writeObject(BinaryWriter.readObject());

            this.seqList = BinaryWriter.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.refresh();
    }

    public void refresh(){
        this.mainTilePane.getChildren().clear();
        for (Sequential seq : this.seqList){
            Image image = new Image(new ByteArrayInputStream(seq.getImage()));
            ImageView view = new ImageView(image);
            this.errorLabel.setVisible(false);

            view.setFitHeight(190);
            view.setPreserveRatio(true);

            Button btn = new Button(seq.getName(), view);
            btn.setStyle("-fx-font-size: 15px;");
            btn.setPrefSize(240,240);
            btn.setGraphic(view);
            btn.setContentDisplay(ContentDisplay.TOP);
            btn.setUserData(seq.getId());
            btn.setOnMouseClicked(this::onClick);
            this.mainTilePane.getChildren().add(btn);
        }
        this.editBtn.setDisable(this.mainTilePane.getChildren().size()==0||this.selectedButton==null);
        this.duplicateBtn.setDisable(this.mainTilePane.getChildren().size()==0||this.selectedButton==null);

        
        Button addBtn = new Button("+");
        addBtn.setStyle("-fx-font-size: 50px;");
        addBtn.setPrefSize(240,240);
        addBtn.setContentDisplay(ContentDisplay.TOP);
        addBtn.setOnMouseClicked(this::onAddBtnClicked);
        this.mainTilePane.getChildren().add(addBtn);
    }

    private void onAddBtnClicked(MouseEvent mouseEvent) {
        this.picta.showCreationScene();
    }

    @FXML
    void onDuplicateBtnClicked(ActionEvent event) {
        if (this.selectedButton != null){
            UUID id = (UUID) this.selectedButton.getUserData();
            for (Sequential seq :this.seqList) {
                if (seq.getId().equals(id)){
                    Sequential newSeq = new Sequential(UUID.randomUUID(), seq.getName(), seq.getDescription(), seq.getImage(), seq.getColor());
                    this.seqList.add(newSeq);
                    break;
                }
            }
            this.refresh();
        }
    }

    @FXML
    void onClick(MouseEvent event) {
        if (event.getSource() instanceof Button && ((Button) event.getSource()).getUserData()!=null) {
            this.selectedButton = (Button) event.getSource();
            this.editBtn.setDisable(false);
            this.duplicateBtn.setDisable(false);
        } else {
            this.selectedButton = null;
            this.editBtn.setDisable(true);
            this.duplicateBtn.setDisable(true);
        }
    }


    @FXML
    private TilePane mainTilePane;


    @FXML
    private Button editBtn;


    public void setMainApplication(Picta picta) {
        this.picta = picta;
    }


    public void onDeleteBtnClicked(ActionEvent actionEvent) {
        if (this.selectedButton != null){
            UUID id = (UUID) this.selectedButton.getUserData();
            for (Sequential seq :this.seqList) {
                if (seq.getId().equals(id)){
                    this.seqList.remove(seq);
                    break;
                }
            }
            this.mainTilePane.getChildren().remove(this.selectedButton);
            try {
                BinaryWriter.writeObject(this.seqList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        refresh();
    }

    public void addSequential(Sequential seq){
        this.seqList.add(seq);
        try {
            BinaryWriter.writeObject(this.seqList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onEditBtnClicked(ActionEvent actionEvent) {
        if (this.selectedButton != null){
            for (Sequential seq :this.seqList) {
                if (seq.getId().equals(this.selectedButton.getUserData())){
                    picta.showEditionScene(seq);
                    break;
                }
            }
        } else {
            this.errorLabel.setVisible(true);
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
        this.picta.showCreationScene();
    }

    @FXML
    void createSequentialByFile(ActionEvent event) {
        this.picta.showCreationScene();
    }

    @FXML
    void editSequentialByFile(ActionEvent event) {
        if (this.selectedButton != null){
            for (Sequential seq : this.seqList) {
                if (seq.getId().equals(this.selectedButton.getUserData())){
                    this.picta.showEditionScene(seq);
                    break;
                }
            }
        } else {
            this.errorLabel.setVisible(true);
        }
    }
}


