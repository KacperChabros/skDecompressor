package com.example.skdecomp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {
    private SkFile file;
    private double ScaleX;
    private double ScaleY;
    @FXML
    TextField pathField;
    @FXML
    TextArea messageField;
    @FXML
    TextField outfileField;
    @FXML
    AnchorPane canvas;
    @FXML
    ScrollPane canvasPane;
    @FXML
    Button treeButton;
    @FXML
    TextField passwordField;
    @FXML
    AnchorPane mainPane;
    @FXML
    void selectFile(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f!=null)
        {
            pathField.setText(f.getAbsolutePath());
        }else{
            pathField.setText(null);
        }
    }
    @FXML
    void decompressFile(ActionEvent e){
        String password=passwordField.getText();
        File outfile = null;
        Messenger messenger;
        try {
            if(pathField.getText() == null || pathField.getText().isEmpty() || pathField.getText().isBlank())
            {
                throw new IOException("No file has been selected");
            }
            this.file = new SkFile(pathField.getText());
            if(outfileField.getText() == null || outfileField.getText().isEmpty() || outfileField.getText().isBlank())
            {
                throw new IOException("No outfile name was given");
            }
            outfile = new File(file.getParent()+"\\"+ outfileField.getText());
            if(!outfile.createNewFile())
                throw new IOException("file with given name already exists in this directory");
        }
        catch(IOException ex){
            String message = "An error has occurred while trying to open the selected file: "+ex.getMessage();
            messenger = new MessengerError(messageField, message);
            return;
        }
        messenger = new MessengerNotify(messageField, "Work in progress...");

        MyThreadListener listener = new MyThreadListener() {
            @Override
            public void threadFinished(SkFile file2) {
                treeButton.setDisable(false);
                file=file2;
            }
        };
        this.file.setPassword(password);
        SkDecomp sde = new SkDecomp(file, outfile ,messageField ,canvas, listener);
        Thread skdThread = new Thread(sde);
        skdThread.start();
    }
    @FXML
    void displayHelp(ActionEvent e)
    {
        Messenger messenger = new MessengerNotify(messageField, "This is help");
    }
    @FXML
    void displayTree(ActionEvent e){
        canvasPane.setVvalue(0);
        canvasPane.setHvalue(0.5);
        canvas.getChildren().clear();
        Messenger messenger;
        if(file.getCompressLevel()==1)
        {
            BTVisualizer btVisualizer = new BTVisualizer(canvas,file.getDictionary());
            btVisualizer.visualize();

        }else{
            messenger= new MessengerError(messageField,"Not CL 1");
        }
        treeButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        messageField.setPrefHeight(primaryScreenBounds.getHeight()-messageField.getLayoutY()-80);
        canvasPane.setPrefHeight(primaryScreenBounds.getHeight()-canvasPane.getLayoutY()-80);
        canvasPane.setPrefWidth(primaryScreenBounds.getWidth()-canvasPane.getLayoutX()-80);
        ScaleX=canvasPane.getScaleX();
        ScaleY=canvasPane.getScaleY();
    }
    @FXML
    void zoomPane(ScrollEvent e)
    {
        /*ScaleX-=0.5;
        double deltaX=e.getDeltaX();
        canvasPane.translateXProperty().set(canvasPane.getTranslateX()+deltaX);
        double deltaY=e.getDeltaY();
        canvasPane.translateYProperty().set(canvasPane.getTranslateY()+deltaY);
        */
    }
}