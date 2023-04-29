module com.example.decompressor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.decompressor to javafx.fxml;
    exports com.example.decompressor;
}