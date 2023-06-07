package fr.damiencren.picta.controller;

import fr.damiencren.picta.MainApplication;
import fr.damiencren.picta.model.*;
import fr.damiencren.picta.utils.NumberUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

public class EditionController implements Initializable {

    private Sequential seq;

    private ImageView selectedImage;

    @FXML
    private TilePane resultTilePane;

    @FXML
    private TextField limitTextField;

    @FXML
    private Label lbSeqDes;

    @FXML
    private Label lbSeqName;

    @FXML
    private RadioButton radioBtnH;

    @FXML
    private RadioButton radioBtnV;

    @FXML
    private VBox picVBox;

    @FXML
    private Label lbError;

    @FXML
    private Label lbUrlPicto;

    @FXML
    private HBox categoryHBox;

    @FXML
    private Label lbIdPicto;

    @FXML
    private ColorPicker seqColorPicker;


    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfName;

    @FXML
    void onModifyDescriptionBtnClicked(ActionEvent event) {
        String des = tfDescription.getText();
        seq.setDescription(des);
        lbSeqDes.setText("");
        refresh();
    }

    @FXML
    void onModifyNameBtnClicked(ActionEvent event) {
        String name = tfName.getText();
        if (name.equals("")){
            lbError.setVisible(true);
            lbError.setText("Le nom ne peut pas être vide");
        } else {
            seq.setName(name);
            lbSeqName.setText("");
            refresh();
        }
    }



    @FXML
    private TextField searchTextField;

    @FXML
    private TilePane seqTilePane;
    private MainApplication mainApplication;

    @FXML
    void onSearchByNameBtnClicked(ActionEvent event) {
        String search = searchTextField.getText();
        if(!NumberUtils.isInteger(limitTextField.getText()))
            return;
        int limit = Integer.parseInt(limitTextField.getText());
        System.out.println(limit);
        ArrayList<Pictogram> pictoList = new ArrayList<>();

        ArrayList<Pictogram> resultList = new ArrayList<>();
        if (search.equals("")){
            resultList = PictogramManager.getAllPictograms();
        } else {
            resultList = PictogramManager.getPictogramsByKeyword(search);
        }

        if (limit > resultList.size()){
            limit = resultList.size();
        }
        for (int i=0; i<limit; i++){
            pictoList.add(resultList.get(i));
        }

        resultTilePane.getChildren().clear();
        for (Pictogram picto : pictoList){
            ImageView view = new ImageView(picto.getUrl());
            view.setPreserveRatio(true);
            view.setFitHeight(200);
            view.setOnDragDetected(dragEvent -> {
                Dragboard dragboard = view.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString(picto.getID());
                dragboard.setContent(content);
                dragEvent.consume();
            });

            resultTilePane.getChildren().add(view);
        }
    }


    public void setSequential(Sequential seq){
        this.seq = seq;
        refresh();
    }
    public void refresh(){
        picVBox.setVisible(false);
        seqTilePane.getChildren().clear();
        if (seq != null) {
            seqTilePane.setStyle("-fx-border-color: "+ seq.getColor().getRGBA() + "; -fx-border-width: 2px;");
            for (Pictogram picto : seq.getPictoList()) {
                Image image = new Image(picto.getUrl());
                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                view.setFitHeight(200);
                view.setUserData(picto.getID());
                view.setOnMouseClicked(MouseEvent -> {
                    if (selectedImage != null) {
                        selectedImage.setEffect(null);
                    }
                    this.selectedImage = view;
                    ColorAdjust highlightEffect = new ColorAdjust();
                    highlightEffect.setBrightness(0.7);
                    selectedImage.setEffect(highlightEffect);

                    lbIdPicto.setText("Id : " +picto.getID());
                    lbUrlPicto.setText("URL : "+picto.getUrl());
                    picVBox.setVisible(true);
                });
                seqTilePane.getChildren().add(view);
            }
            lbSeqName.setText(seq.getName());
            lbSeqDes.setText(seq.getDescription());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("Alimentation", "Loisirs", "Lieu", "Education", "Temps", "Mobilité", "Religion", "Travail", "Santé", "Communication", "Objet"));
        for (String category : categories){
            Button btn = new Button(category);
            btn.setOnAction(event -> {
                searchTextField.setText(category);
                onSearchByNameBtnClicked(event);
                refresh();
            });
            categoryHBox.getChildren().add(btn);
        }

        seqTilePane.setOnDragOver(event -> {
            if (event.getGestureSource() != seqTilePane && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        seqTilePane.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String pictoUrl = dragboard.getString();
                seq.addPictogram(PictogramManager.getPictogramById(pictoUrl));
                success = true;
                refresh();
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    void leaveBtnClicked(ActionEvent event) {
        mainApplication.showMainView();
    }

    @FXML
    void saveBtnClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        ArrayList<Sequential> seqList = BinaryWriter.readObject();
        for (Sequential seq : seqList) {
            if (seq.getId().equals(this.seq.getId())) {
                seqList.remove(seq);
                seqList.add(this.seq);
                BinaryWriter.writeObject(seqList);
                return;
            }
        }
    }

    @FXML
    void pictoDelBtnClcked(ActionEvent event) {
        if (selectedImage != null) {
            seqTilePane.getChildren().remove(selectedImage);
            seq.delWithId((String) selectedImage.getUserData());
            System.out.println(selectedImage.getUserData());
            selectedImage = null;
        }
    }


    @FXML
    void radioBtnHClicked(ActionEvent event) {
        seqTilePane.setOrientation(Orientation.HORIZONTAL);
        radioBtnH.setSelected(true);
        radioBtnV.setSelected(false);
        refresh();
    }

    @FXML
    void radioBtnVClicked(ActionEvent event) {
        seqTilePane.setOrientation(Orientation.VERTICAL);
        radioBtnH.setSelected(false);
        radioBtnV.setSelected(true);

        refresh();
    }


}
