module com.example.picta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.picta to javafx.fxml;
    exports com.example.picta;
    exports com.example.picta.controller;
    opens com.example.picta.controller to javafx.fxml;
}