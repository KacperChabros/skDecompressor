package com.example.decompressor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller{
    @FXML
    TextField pathField;
    @FXML
    TextArea messageField;
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
        SkFile file;
        try {
            if(pathField.getText() == null || pathField.getText().isEmpty() || pathField.getText().isBlank())
            {
                throw new IOException("No file has been selected");
            }
                file = new SkFile(pathField.getText());
                //messageField.setText(Long.toString(file.length()));
        }
        catch(IOException ex){
            String message = "An error has occurred while trying to open the selected file: "+ex.getMessage();
            Messenger messenger = new MessengerError(messageField, message);

            return;
        }
        SkDecomp sde = new SkDecomp(file,messageField);
        Thread skdThread = new Thread(sde);
        skdThread.start();
        //sde.start();
    }
    @FXML
    void displayHelp(ActionEvent e)
    {
        Messenger messenger = new MessengerNotify(messageField, "This is help");
    }

}