package com.example.decompressor;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class Messenger {
    protected TextArea messageField;

    public Messenger(TextArea messageField) {
        this.messageField=messageField;
    }


    public abstract void displayMessage(String message);

    protected abstract void setStyle();
}
