module com.example.tugasbesaruap {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.codelab.tugasbesaruap to javafx.fxml;
    exports com.codelab.tugasbesaruap;
}