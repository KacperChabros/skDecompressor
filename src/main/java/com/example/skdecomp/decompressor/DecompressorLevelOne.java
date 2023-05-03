package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

public class DecompressorLevelOne implements Decompressor{
    private SkFile file;
    public DecompressorLevelOne(SkFile file){
        this.file = file;
    }
    @Override
    public void decompress() {

    }
}
