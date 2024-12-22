module com.codelab.tugasbesaruap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;



    opens com.codelab.tugasbesaruap to javafx.fxml;
    exports com.codelab.tugasbesaruap;
}