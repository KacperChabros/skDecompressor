package com.example.decompressor;

import javafx.scene.control.TextArea;

public class MessengerError extends Messenger{

    public MessengerError(TextArea messageField, String message)
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
        messageField.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
    }
}
