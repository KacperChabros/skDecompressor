package com.example.skdecomp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller{
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
        //SkFile file;
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
                //helpButton.setDisable(false);
                treeButton.setDisable(false);
                file=file2;
            }
        };
        this.file.setPassword(password);
        System.out.println(file.getPassword());
        SkDecomp sde = new SkDecomp(file, outfile ,messageField ,canvas, listener);
        Thread skdThread = new Thread(sde);
        skdThread.start();
        /*try {
            skdThread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }*/
        /*
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                BTVisualizer btVisualizer=new BTVisualizer(canvas,sde.getFile().getDictionary());
                btVisualizer.visualize();
                canvasPane.setVvalue(0);
                canvasPane.setHvalue(0.5);
            }
        });*/
        /*
        BTVisualizer btVisualizer=new BTVisualizer(canvas,sde.getFile().getDictionary());
        btVisualizer.visualize();
        canvasPane.setVvalue(0);
        canvasPane.setHvalue((double) 1 /2);*/
        /*
        final BTVisualizer[] btVisualizer = new BTVisualizer[1];
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!skdThread.isAlive())
                {
                    btVisualizer[0] = new BTVisualizer(canvas, sde.getFile().getDictionary());
                    btVisualizer[0].visualize();
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 250);
*/

        /*if(!skdThread.isAlive())
            messenger = new MessengerSuccess(messageField, "Done!");*/
        //sde.start();
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
        //canvas.setMaxWidth(5000);
        //canvas.setPrefWidth(5000);
        //System.out.println(canvas.getWidth());
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
    /*@FXML
    public void initialize() {
        messageField.setPrefHeight(mainPane.getHeight()-messageField.getTranslateY());
    }*/
}