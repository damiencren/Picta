module com.example.picta {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.picta to javafx.fxml;
    exports com.example.picta;
}