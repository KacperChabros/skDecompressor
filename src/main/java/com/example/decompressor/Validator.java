package com.example.decompressor;

import javafx.fxml.FXML;

public class Validator {
    private final byte[] tBytes;
    private final byte checksum;
    private boolean valid;
    private byte tmpSum;
    public Validator(byte[] tBytes){
        this.tBytes=tBytes;
        this.checksum= (byte) 0b10001001;
        this.valid=false;
        //System.out.println(checksum);
        //isValid();
    }
    public void validate() throws InvalidFileException{
        /*boolean isCyphered=((tBytes[2]&0b00100000)!=0);
        byte compressLevel= tBytes[2];
        compressLevel= (byte) (compressLevel>>6);
        compressLevel= (byte) (compressLevel&0b00000011);
        System.out.println("Cypher:"+isCyphered+"|CompressLevel:"+compressLevel);
        //byte tmpSum;*/
        if(tBytes.length<8)
        {
            this.valid=false;
            //TODO: WIADOMOŚĆ BŁĘDU
            throw new InvalidFileException("File has not been compressed by skComp");
            //return;
        }
        if(tBytes[0]!='S'||tBytes[1]!='K')
        {
            this.valid=false;
            //TODO: WIADOMOŚĆ BŁĘDU
            throw new InvalidFileException("File has not been compressed by skComp");
            //return;
        }
        boolean isCyphered=((tBytes[2]&0b00100000)!=0);
        byte compressLevel= tBytes[2];
        compressLevel= (byte) (compressLevel>>6);
        compressLevel= (byte) (compressLevel&0b00000011);
        System.out.println("Cypher:"+isCyphered+"|CompressLevel:"+compressLevel);
        if(isCyphered) {
            //decypher();
        }
        tmpSum=tBytes[3];
        makeSum();
        System.out.println("TmpSum:"+tmpSum+"|Checksum"+checksum);
        if(tmpSum!=checksum)
        {
            this.valid=false;
            throw new InvalidFileException("File is corrupted or wrong password was given");
        }
        this.valid=true;
    }
    public boolean IsValid(){
        return valid;
    }
    private void makeSum(){
        for(int i=8;i<tBytes.length;i++)
        {
            tmpSum= (byte) (tmpSum^tBytes[i]);
        }
        for(int i=4;i<8;i++)
        {
            tmpSum= (byte) (tmpSum^tBytes[i]);
        }
    }
}
