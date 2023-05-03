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
        /*DictionaryTrie tire = new DictionaryTrie();
        tire.insert("111110", 'Y');
        tire.insert("111111", 'W');
        tire.insert("111100", 'S');
        tire.insert("111101", 'I');
        tire.insert("1001", 'N');
        System.out.println(tire.lookForSymbol("1001"));
        System.out.println(tire.lookForSymbol("111101"));
        System.out.println(tire.lookForSymbol("111100"));
        System.out.println(tire.lookForSymbol("111111"));
        System.out.println(tire.lookForSymbol("111110"));
        System.out.println(tire.lookForSymbol("110110"));
        System.out.println(tire.lookForSymbol("1111100"));
        System.out.println(tire.lookForSymbol("100"));
        System.out.println(tire.lookForSymbol("1000"));*/

    }

}