package com.example.skdecomp;

import com.example.skdecomp.decompressor.DictionaryTrie;

import java.io.File;

public class SkFile extends File {
    private byte checksum;
    private int dictLength;
    private byte compressLevel;
    private boolean isCyphered;
    private byte importantBitsInLastCompressedByte;
    private byte numberOfNotCompressedBytes;
    private byte importantBitsOfLastDictionaryByte;
    private byte [] allBytes;

    public DictionaryTrie getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryTrie dictionary) {
        this.dictionary = dictionary;
    }

    private DictionaryTrie dictionary;
    public SkFile(String pathname) {
        super(pathname);
    }

    public void setAllBytes(byte [] tBytes){
        this.allBytes=tBytes;
    }

    public byte[] getAllBytes() {
        return allBytes;
    }

    public byte getChecksum() {
        return checksum;
    }

    public long getDictLength(){
        return dictLength;
    }

    public byte getCompressLevel(){
        return compressLevel;
    }

    public boolean getCyphered() {
        return isCyphered;
    }

    public byte getImportantBitsInLastCompressedByte(){
        return importantBitsInLastCompressedByte;
    }

    public byte getNumberOfNotCompressedBytes(){
        return numberOfNotCompressedBytes;
    }

    public byte getImportantBitsOfLastDictionaryByte(){
        return importantBitsOfLastDictionaryByte;
    }

    public void setChecksum(byte checksum) {
        this.checksum = checksum;
    }

    public boolean isCyphered() {
        return isCyphered;
    }

    public void setCompressLevel(byte compressLevel) {
        this.compressLevel = compressLevel;
    }

    public void setCyphered(boolean cyphered) {
        isCyphered = cyphered;
    }

    public void setDictLength(int dictLength) {
        this.dictLength = dictLength;
    }

    public void setImportantBitsInLastCompressedByte(byte importantBitsInLastCompressedByte) {
        this.importantBitsInLastCompressedByte = importantBitsInLastCompressedByte;
    }

    public void setImportantBitsOfLastDictionaryByte(byte importantBitsOfLastDictionaryByte) {
        this.importantBitsOfLastDictionaryByte = importantBitsOfLastDictionaryByte;
    }

    public void setNumberOfNotCompressedBytes(byte numberOfNotCompressedBytes) {
        this.numberOfNotCompressedBytes = numberOfNotCompressedBytes;
    }

}
