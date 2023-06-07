module com.example.picta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    //requires com.fasterxml.jackson.databind;


    opens fr.damiencren.picta to javafx.fxml;
    exports fr.damiencren.picta;
    exports fr.damiencren.picta.controller;
    opens fr.damiencren.picta.controller to javafx.fxml;
}