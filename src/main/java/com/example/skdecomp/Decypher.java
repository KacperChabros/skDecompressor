package com.example.skdecomp;

public class Decypher {
    private int text;
    private int charkey;
    private int dictLength;
    private String key;
    private int keyLength;
    private byte compressLevel;
    private byte [] tabBytes;
    private byte checksum;
    public Decypher(byte []tabBytes, byte checksum, String key){
        this.tabBytes=tabBytes;
        this.checksum=checksum;
        this.key=key;
        this.text=0;
        this.charkey=0;
        this.keyLength=key.length();
        makeCompressLevel();
        makeDictLength();
    }
    private void makeCompressLevel(){
        byte flag1=tabBytes[2];
        flag1=(byte)(flag1>>6);
        this.compressLevel=(byte)(flag1&0b00000011);
    }
    private void makeDictLength(){
        int tempDictLength=0;
        tempDictLength+=tabBytes[4]&0xff;
        tempDictLength=tempDictLength<<8;

        tempDictLength+=tabBytes[5]&0xff;
        tempDictLength=tempDictLength<<8;

        tempDictLength+=tabBytes[6]&0xff;
        this.dictLength=tempDictLength;
    }
    public void runDecypher(){
        if(this.compressLevel!=0)
        {
            runDecypherNotZero();
        }else{
            runDecypherZero();
        }
    }
    private void runDecypherZero(){
        for(int i=8;i<tabBytes.length;i++)
        {
            text=tabBytes[i]-'A';
            charkey=key.charAt(i%this.keyLength)-'A';
            tabBytes[i]= (byte) ((text^charkey)+'A');
        }
    }
    private void runDecypherNotZero(){
        for(int i=8;i<this.dictLength+8;i++)
        {
            text=tabBytes[i]-'A';
            charkey=key.charAt(i%this.keyLength)-'A';
            tabBytes[i]= (byte) ((text^charkey)+'A');
        }
    }
    public byte [] getTabBytes(){
        return tabBytes;
    }
}
