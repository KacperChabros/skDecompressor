package com.example.decompressor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SKdecompress");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();    //setting bounds of stage to make it
        stage.setX(primaryScreenBounds.getMinX());                                  //maximized and not resizable
        stage.setY(primaryScreenBounds.getMinY());

        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setMaxWidth(primaryScreenBounds.getWidth());
        stage.setMinWidth(primaryScreenBounds.getWidth());

        stage.setMaxHeight(primaryScreenBounds.getHeight());
        stage.setMinHeight(primaryScreenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}