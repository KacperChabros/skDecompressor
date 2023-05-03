module com.example.decompressor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.skdecomp to javafx.fxml;
    exports com.example.skdecomp;
    exports com.example.skdecomp.decompressor;
    opens com.example.skdecomp.decompressor to javafx.fxml;
}