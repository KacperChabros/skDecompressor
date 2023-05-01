package com.example.decompressor;

import javafx.fxml.FXML;

public class Validator {
    private byte[] tBytes;
    private int checksum;
    private boolean valid;
    public Validator(byte[] tBytes){
        this.tBytes=tBytes;
        this.checksum=137;
        //isValid();
    }
    public void validate(){
        if(tBytes[0]!='S'||tBytes[1]!='K')
        {
            this.valid=false;
        }else{

        }
    }
    public boolean IsValid(){
        return valid;
    }
}
