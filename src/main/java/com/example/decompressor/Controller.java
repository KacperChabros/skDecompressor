package com.example.decompressor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Controller {
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
            if(pathField.getText().isEmpty())
            {
                throw new Exception("No file has been selected");
            }
                file = new SkFile(pathField.getText());
                //messageField.setText(Long.toString(file.length()));
        }
        catch(Exception er){
            messageField.setText("An error has occurred while trying to open the selected file: "+er.getMessage());
            return;
        }
        SkDecomp sde = new SkDecomp(file,messageField);
        sde.start();
    }
    @FXML
    void displayHelp(ActionEvent e)
    {
        messageField.setText("I am displaying Help");
    }
}