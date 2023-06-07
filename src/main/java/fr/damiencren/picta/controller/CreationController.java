package fr.damiencren.picta.controller;

import fr.damiencren.picta.MainApplication;
import fr.damiencren.picta.model.Sequential;
import fr.damiencren.picta.model.SerializableColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class CreationController implements Initializable {
    private MainApplication mainApplication;
    private static final String DEFAULT_PATH = "/fr/damiencren/picta/default.png";

    private File imageFile;

    @FXML
    private ColorPicker colorPicker;


    @FXML
    private Label lbError;

    @FXML
    private TextField descriptionTextField;


    @FXML
    private Label lbImagePath;

    @FXML
    private TextField nameTextField;

    @FXML
    private ImageView previewImageView;

    @FXML
    void onCancelBtnClicked(ActionEvent event) {
        mainApplication.getSecondStage().close();
        refresh();
    }

    @FXML
    void onCreateBtnClicked(ActionEvent event) throws IOException {
        if (nameTextField.getText().isEmpty()) {
            lbError.setVisible(true);
            lbError.setText("Name field is empty");
            return;
        }
        SerializableColor color = new SerializableColor(colorPicker.getValue());
        BufferedImage image = ImageIO.read(imageFile);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        mainApplication.getHelloController().addSequential(new Sequential(UUID.randomUUID(),nameTextField.getText(), descriptionTextField.getText(), imageBytes, color));
        mainApplication.getSecondStage().close();
        mainApplication.getHelloController().refresh();
        refresh();
    }

    @FXML
    void onFileBrowserBtnClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(mainApplication.getSecondStage());

        if (selectedFile != null) {
            try {
                this.imageFile = selectedFile;
                previewImageView.setImage(new Image(selectedFile.toURI().toString()));
                lbImagePath.setText(selectedFile.getPath());
            } catch (Exception e) {
                lbError.setVisible(true);
                lbError.setText("File not found");
            }
        }

    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void refresh() {
        this.imageFile = new File(getClass().getResource(DEFAULT_PATH).getFile());
        Image image = new Image(imageFile.toURI().toString());
        previewImageView.setImage(image);
        lbImagePath.setText(imageFile.getPath());
        lbError.setVisible(false);
        nameTextField.setText("");
        descriptionTextField.setText("");
        colorPicker.setValue(Color.RED);
    }
}
