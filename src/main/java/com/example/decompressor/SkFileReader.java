package com.example.decompressor;

import java.io.IOException;
import java.nio.file.Files;

public class SkFileReader {
    private SkFile file;
    private Validator validator;
    public SkFileReader(SkFile file) throws IOException{
        this.file=file;
        this.file.setAllBytes(Files.readAllBytes(this.file.toPath()));
        this.validator=new Validator(file.getAllBytes());
        //validator.validate();
        //skReadHeader();
    }
    private void skReadHeader() {
        byte flag1=file.getAllBytes()[2];
        byte flag2=file.getAllBytes()[7];
        int tempDictLength=0;
        file.setImportantBitsInLastCompressedByte((byte) (flag1 & 0b00001111));
        flag1= (byte) (flag1>>5);
        file.setCyphered((flag1 & 0b00000001) == 1);
        flag1= (byte) (flag1>>1);
        file.setCompressLevel((byte) (flag1 & 0b00000011));
        file.setChecksum(file.getAllBytes()[3]);
        //0xff - Casting to unsigned byte
        tempDictLength+=(file.getAllBytes()[4]&0xff);
        tempDictLength=tempDictLength<<8;
        tempDictLength+=(file.getAllBytes()[5]&0xff);
        tempDictLength=tempDictLength<<8;
        tempDictLength+=(file.getAllBytes()[6]&0xff);
        file.setDictLength(tempDictLength);
        file.setImportantBitsOfLastDictionaryByte((byte) (flag2&0b00001111));
        flag2= (byte) (flag2>>6);
        file.setNumberOfNotCompressedBytes((byte) (flag2&0b00000011));
        System.out.println("SkReadHeader wykonał zadanie :)");
        //System.out.println(file.getImportantBitsOfLastDictionaryByte()+"|"+file.getNumberOfNotCompressedBytes());
        //System.out.println(file.getDictLength()+"|"+file.getImportantBitsInLastCompressedByte()+"|"+file.getCompressLevel()+"|"+file.getCyphered());
    }
    public void readFile() throws InvalidFileException{
        //try {
            validator.validate();
            skReadHeader();

            //System.out.println(validator.IsValid());
        //}catch(InvalidFileException ex){
            //TODO:WIADOMOŚĆ MESSAGEFIELD
            //System.out.println("Error:"+ex.getMessage());
        //}
    }
}
