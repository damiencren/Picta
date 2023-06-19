package fr.iclipse.picta.controller;

import fr.iclipse.picta.Picta;
import fr.iclipse.picta.model.Sequential;
import fr.iclipse.picta.model.SerializableColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.net.URL;
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

public class CreateController implements Initializable {
    private Picta picta;

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
        this.picta.getSecondStage().close();
        this.refresh();
    }

    @FXML
    void onCreateBtnClicked(ActionEvent event) throws IOException {
        if (this.nameTextField.getText().isEmpty()) {
            this.lbError.setVisible(true);
            this.lbError.setText("Name field is empty");
            return;
        }
        SerializableColor color = new SerializableColor(this.colorPicker.getValue());
        BufferedImage image = ImageIO.read(this.imageFile);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        this.picta.getHelloController().addSequential(new Sequential(UUID.randomUUID(),this.nameTextField.getText(), this.descriptionTextField.getText(), imageBytes, color));
        this.picta.getSecondStage().close();
        this.picta.getHelloController().refresh();
        this.refresh();
    }

    @FXML
    void onFileBrowserBtnClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(this.picta.getSecondStage());

        if (selectedFile != null) {
            try {
                this.imageFile = selectedFile;
                this.previewImageView.setImage(new Image(selectedFile.toURI().toString()));
                this.lbImagePath.setText(selectedFile.getPath());
            } catch (Exception e) {
                this.lbError.setVisible(true);
                this.lbError.setText("File not found");
            }
        }

    }

    public void setMainApplication(Picta picta) {
        this.picta = picta;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void refresh() {
        this.imageFile = new File(getClass().getResource("/fr/iclipse/picta/default.png").getFile());
        Image image = new Image(this.imageFile.toURI().toString());
        this.previewImageView.setImage(image);
        this.lbImagePath.setText(this.imageFile.getPath());
        this.lbError.setVisible(false);
        this.nameTextField.setText("");
        this.descriptionTextField.setText("");
        this.colorPicker.setValue(Color.RED);
    }
}
