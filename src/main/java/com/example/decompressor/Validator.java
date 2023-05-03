package com.example.decompressor;

public class Validator {
    private final byte[] tBytes;
    private final byte checksum;
    private boolean valid;
    private byte tmpSum;
    public Validator(byte[] tBytes){
        this.tBytes=tBytes;
        this.checksum= (byte) 0b10001001;
        this.valid=false;
    }
    public void validate() throws InvalidFileException{
        if(tBytes.length<8)
        {
            this.valid=false;
            throw new InvalidFileException("File has not been compressed by skComp");
        }
        if(tBytes[0]!='S'||tBytes[1]!='K')
        {
            this.valid=false;
            throw new InvalidFileException("File has not been compressed by skComp");
        }
        boolean isCyphered=((tBytes[2]&0b00100000)!=0);
        byte flag1= tBytes[2];
        flag1= (byte) (flag1>>6);
        flag1= (byte) (flag1&0b00000011);

        if(isCyphered) {
            //TODO: decypher();
        }
        tmpSum=tBytes[3];
        makeFileChecksum();

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

    private void makeFileChecksum(){
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
