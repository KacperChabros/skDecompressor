package com.example.skdecomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class Controller{
    @FXML
    TextField pathField;
    @FXML
    TextArea messageField;
    @FXML
    TextField outfileField;
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
        File outfile = null;
        Messenger messenger;
        try {
            if(pathField.getText() == null || pathField.getText().isEmpty() || pathField.getText().isBlank())
            {
                throw new IOException("No file has been selected");
            }
            file = new SkFile(pathField.getText());
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
        SkDecomp sde = new SkDecomp(file, outfile ,messageField );
        Thread skdThread = new Thread(sde);
        skdThread.start();
        /*if(!skdThread.isAlive())
            messenger = new MessengerSuccess(messageField, "Done!");*/
        //sde.start();
    }
    @FXML
    void displayHelp(ActionEvent e)
    {
        Messenger messenger = new MessengerNotify(messageField, "This is help");

    }

}