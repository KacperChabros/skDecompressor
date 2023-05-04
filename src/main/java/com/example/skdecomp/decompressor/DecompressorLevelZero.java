package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecompressorLevelZero implements Decompressor{
    private SkFile file;
    private File outfile;
    public DecompressorLevelZero(SkFile file, File outfile){
        this.file = file;
        this.outfile = outfile;
    }
    @Override
    public void decompress() throws IOException{
        byte[] allBytes = file.getAllBytes();
        FileOutputStream outputStream = new FileOutputStream(outfile, true);
        for(int i = 8; i < allBytes.length; i++)
        {
            outputStream.write( allBytes[i] );      //rewrite all the symbols except for header of the SkFile
        }
        outputStream.close();
    }
}
