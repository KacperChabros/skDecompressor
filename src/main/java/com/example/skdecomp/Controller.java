package com.example.skdecomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private SkFile file;
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
    Button decompressButton;
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
            public void threadFinished() {
                decompressButton.setDisable(false);
            }

            @Override
            public void threadFinishedSuccesfully(SkFile file2) {
                treeButton.setDisable(false);
                file=file2;
                this.threadFinished();
            }
        };
        this.file.setPassword(password);
        decompressButton.setDisable(true);
        SkDecomp sde = new SkDecomp(file, outfile, messageField, listener);
        Thread skdThread = new Thread(sde);
        skdThread.start();
    }
    @FXML
    void displayHelp(ActionEvent e)
    {
        String message = "This is decompressor for files compressed with skcomp.\nTo decompress the file follow the steps:\n1. select a compressed " +
                "file with the appropriate button.\n2. provide a name for the outfile (with the extension). The outfile will be located" +
                "in the same folder as compressed file.\n3. Provide a password if your file is cyphered.\n4. Click Decompress button.\n" +
                "Note: If your file was compressed with level 1 compression, you can display the tree with Display Tree button. The canvas can be zoomed in " +
                "and out using appropriate buttons.";
        Messenger messenger = new MessengerNotify(messageField, message);

    }
    @FXML
    void displayTree(ActionEvent e){
            canvasPane.setVvalue(0);
            canvasPane.setHvalue(0.5);
            canvas.getChildren().clear();
            Messenger messenger;
            if (file.getCompressLevel() == 1) {
                BTVisualizer btVisualizer = new BTVisualizer(canvas, file.getDictionary());
                btVisualizer.visualize();

            } else {
                messenger = new MessengerError(messageField, "Displaying tree is supported only for compression level 1. " +
                        "This file does not meet the requirements");
            }
            treeButton.setDisable(true);

    }

    @FXML
    void zoomIn(ActionEvent e)
    {
        canvas.setScaleX(canvas.getScaleX() + 0.1);
        canvas.setScaleY(canvas.getScaleY() + 0.1);
    }
    @FXML
    void zoomOut(ActionEvent e)
    {
        canvas.setScaleX(canvas.getScaleX() - 0.1);
        canvas.setScaleY(canvas.getScaleY() - 0.1);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        messageField.setPrefHeight(primaryScreenBounds.getHeight()-messageField.getLayoutY()-80);
        canvasPane.setPrefHeight(primaryScreenBounds.getHeight()-canvasPane.getLayoutY()-80);
        canvasPane.setPrefWidth(primaryScreenBounds.getWidth()-canvasPane.getLayoutX()-80);
    }

}