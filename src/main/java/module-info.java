module fr.iclipse.picta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens fr.iclipse.picta to javafx.fxml;
    exports fr.iclipse.picta;
    exports fr.iclipse.picta.controller;
    opens fr.iclipse.picta.controller to javafx.fxml;
}