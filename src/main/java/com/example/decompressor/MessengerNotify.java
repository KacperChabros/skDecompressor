package com.example.decompressor;

import javafx.scene.control.TextArea;

public class MessengerNotify extends Messenger{

    public MessengerNotify(TextArea messageField, String message)
    {
        super(messageField);
        displayMessage(message);
    }
    @Override
    public void displayMessage(String message) {
        setStyle();
        this.messageField.setText(message);
    }

    @Override
    protected void setStyle() {
        messageField.setStyle("-fx-font-size: 20px;");
    }
}
