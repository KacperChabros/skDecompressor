package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

import java.io.File;

public class DecompressorLevel3Factory implements DecompressorFactory{
    @Override
    public DictionaryReader createDictionaryReader(SkFile file) {
        return new DictionaryReaderLevelThree(file);
    }

    @Override
    public Decompressor createDecompressor(SkFile file, File outfile) {
        return new DecompressorLevelThree(file, outfile);
    }
}
