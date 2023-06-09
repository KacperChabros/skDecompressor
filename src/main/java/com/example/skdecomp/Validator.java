package com.example.skdecomp;

public class Validator {
    private byte[] tBytes;
    private final byte checksum;
    private boolean valid;
    private byte tmpSum;
    private String password;
    public Validator(byte[] tBytes,String password){
        this.tBytes=tBytes;
        this.checksum= (byte) 0b10001001;
        this.valid=false;
        this.password=password;
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

        if(isCyphered && !this.password.isBlank()) {
            Decypher decypher = new Decypher(this.tBytes,this.checksum,this.password);
            decypher.runDecypher();
            this.tBytes= decypher.getTabBytes();
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
    public byte[] gettBytes()
    {
        return this.tBytes;
    }
}
