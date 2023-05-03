package com.example.decompressor;

public class InvalidFileException extends Exception{
    public InvalidFileException(String message){
        super(message);
    }
}
