package fr.iclipse.picta.controller;

import fr.iclipse.picta.Picta;
import fr.iclipse.picta.utils.BinaryWriter;
import fr.iclipse.picta.model.Pictogram;
import fr.iclipse.picta.utils.NumberUtils;
import fr.iclipse.picta.utils.SwingFXUtils;
import fr.iclipse.picta.model.PictogramManager;
import fr.iclipse.picta.model.Sequential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.scene.layout.TilePane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EditController implements Initializable {

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
        this.seq.setDescription(this.tfDescription.getText());
        this.lbSeqDes.setText("");
        this.refresh();
    }

    @FXML
    void onModifyNameBtnClicked(ActionEvent event) {
        String name = this.tfName.getText();
        if (name.equals("")) {
            this.lbError.setVisible(true);
            this.lbError.setText("Le nom ne peut pas être vide");
        } else {
            this.seq.setName(name);
            this.lbSeqName.setText("");
            this.refresh();
        }
    }


    @FXML
    private TextField searchTextField;

    @FXML
    private TilePane seqTilePane;
    private Picta picta;

    @FXML
    void onSearchByNameBtnClicked(ActionEvent event) {
        String search = this.searchTextField.getText();
        if (!NumberUtils.isInteger(this.limitTextField.getText()))
            return;
        int limit = Integer.parseInt(this.limitTextField.getText());
        System.out.println(limit);
        ArrayList<Pictogram> pictoList;

        ArrayList<Pictogram> resultList = search.equals("") ? PictogramManager.getAllPictograms() : PictogramManager.getPictogramsByKeyword(search);

        if (limit > resultList.size()) {
            limit = resultList.size();
        }
        pictoList = IntStream.range(0, limit).mapToObj(resultList::get).collect(Collectors.toCollection(ArrayList::new));

        this.resultTilePane.getChildren().clear();
        for (Pictogram picto : pictoList) {
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

            this.resultTilePane.getChildren().add(view);
        }
    }


    public void setSequential(Sequential seq) {
        this.seq = seq;
        this.refresh();
    }

    public void refresh() {
        this.picVBox.setVisible(false);
        this.seqTilePane.getChildren().clear();
        if (this.seq != null) {
            this.seqTilePane.setStyle("-fx-border-color: " + this.seq.getColor().getRGBA() + "; -fx-border-width: 2px;");
            for (Pictogram picto : this.seq.getPictoList()) {
                Image image = new Image(picto.getUrl());
                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                view.setFitHeight(200);
                view.setUserData(picto.getID());
                view.setOnMouseClicked(mouseEvent -> {
                    if (this.selectedImage != null) {
                        this.selectedImage.setEffect(null);
                    }
                    this.selectedImage = view;
                    ColorAdjust highlightEffect = new ColorAdjust();
                    highlightEffect.setBrightness(0.7);
                    this.selectedImage.setEffect(highlightEffect);

                    this.lbIdPicto.setText("Id : " + picto.getID());
                    this.lbUrlPicto.setText("URL : " + picto.getUrl());
                    this.picVBox.setVisible(true);
                });
                this.seqTilePane.getChildren().add(view);
            }
            this.lbSeqName.setText(this.seq.getName());
            this.lbSeqDes.setText(this.seq.getDescription());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("Alimentation", "Loisirs", "Lieu", "Education", "Temps", "Mobilité", "Religion", "Travail", "Santé", "Communication", "Objet"));
        for (String category : categories) {
            Button btn = new Button(category);
            btn.setOnAction(event -> {
                this.searchTextField.setText(category);
                onSearchByNameBtnClicked(event);
                refresh();
            });
            this.categoryHBox.getChildren().add(btn);
        }

        this.seqTilePane.setOnDragOver(event -> {
            if (event.getGestureSource() != this.seqTilePane && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        seqTilePane.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String pictoUrl = dragboard.getString();
                this.seq.addPictogram(PictogramManager.getPictogramById(pictoUrl));
                success = true;
                this.refresh();
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void setMainApplication(Picta picta) {
        this.picta = picta;
    }

    @FXML
    void leaveBtnClicked(ActionEvent event) {
        this.picta.showMainView();
    }

    @FXML
    void saveBtnClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        ArrayList<Sequential> seqList = BinaryWriter.readObject();
        for (Sequential seq : seqList) {
            if (seq.getId().equals(this.seq.getId())) {
                seqList.remove(seq);
                seqList.add(this.seq);
                BinaryWriter.writeObject(seqList);
                lbError.setVisible(true);
                lbError.setText("Sauvegarde réussie");
                return;
            }
        }


    }

    @FXML
    void pictoDelBtnClcked(ActionEvent event) {
        if (this.selectedImage != null) {
            this.seqTilePane.getChildren().remove(this.selectedImage);
            this.seq.removeById((String) this.selectedImage.getUserData());
            this.selectedImage = null;
        }
    }


    @FXML
    void radioBtnHClicked(ActionEvent event) {
        this.seqTilePane.setOrientation(Orientation.HORIZONTAL);
        this.radioBtnH.setSelected(true);
        this.radioBtnV.setSelected(false);
        this.refresh();
    }

    @FXML
    void radioBtnVClicked(ActionEvent event) {
        this.seqTilePane.setOrientation(Orientation.VERTICAL);
        this.radioBtnH.setSelected(false);
        this.radioBtnV.setSelected(true);
        this.refresh();
    }

    @FXML
    void onMoveLeftBtnClicked(ActionEvent event) {
        if (this.selectedImage == null)
            return;
        int index = this.getSelectedIndex();
        if (index == -1)
            return;
        this.seq.switchElements(index, index + 1);
        this.refresh();
        this.selectedImage = null;
    }

    @FXML
    void onMoveRightBtnClicked(ActionEvent event) {
        if (this.selectedImage == null)
            return;
        int index = this.getSelectedIndex() + 1;
        if (index >= this.seq.getPictoList().size() || index + 1 >= this.seq.getPictoList().size())
            return;
        this.seq.switchElements(index + 1, index);
        this.refresh();
        this.selectedImage = null;
    }

    private int getSelectedIndex() {
        int index = 0;
        int k = -1;
        for (Pictogram picto : this.seq.getPictoList()) {
            if (picto.getID().equals(this.selectedImage.getUserData())) {
                index = k;
            }
            k++;
        }
        return index;
    }

    @FXML
    void onModifyColorBtnClicked(ActionEvent event) {
        Color newColor = this.seqColorPicker.getValue();
        if (newColor != null) {
            this.seq.setColor(newColor);
            this.refresh();
        }
        refresh();
    }

    @FXML
    void downloadBtnClicked(ActionEvent event) {
        this.tilePaneToPdf();
        this.lbError.setVisible(true);
        lbError.setText("Séquentiel téléchargé");
    }

    private void tilePaneToPdf() {
        try (PDDocument document = new PDDocument()) {
            PDPage currentPage = null;
            PDPageContentStream contentStream = null;
            int imageCount = 0;

            for (Node node : this.seqTilePane.getChildren()) {
                WritableImage image = node.snapshot(new SnapshotParameters(), null);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

                if (imageCount % 6 == 0) {
                    if (currentPage != null) {
                        contentStream.close();
                    }
                    currentPage = new PDPage();
                    document.addPage(currentPage);
                    contentStream = new PDPageContentStream(document, currentPage);
                    imageCount = 0;
                }

                float x = (float) ((imageCount % 2) * image.getWidth());
                float y = (float) (currentPage.getMediaBox().getHeight() - ((imageCount / 2 + 1) * image.getHeight()));

                contentStream.drawImage(pdImage, x, y, (float) image.getWidth(), (float) image.getHeight());
                imageCount++;
            }
            if (contentStream != null)
                contentStream.close();
            document.save(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + this.seq.getName() + ".pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
