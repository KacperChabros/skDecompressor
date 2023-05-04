package com.example.skdecomp;

import javafx.scene.control.TextArea;

public abstract class Messenger {
    protected TextArea messageField;

    public Messenger(TextArea messageField) {
        this.messageField=messageField;
    }


    public abstract void displayMessage(String message);

    protected abstract void setStyle();
}
