module com.example.picta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.example.picta to javafx.fxml;
    exports com.example.picta;
    exports com.example.picta.controller;
    opens com.example.picta.controller to javafx.fxml;
}