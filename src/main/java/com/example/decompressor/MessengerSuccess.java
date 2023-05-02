package com.example.decompressor;

import javafx.scene.control.TextArea;

public class MessengerSuccess extends Messenger{

    public MessengerSuccess(TextArea messageField, String message)
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
        messageField.setStyle("-fx-text-fill: green; -fx-font-size: 22px;");
    }
}
