package com.example.decompressor;

public class DecompressorLevel1Factory implements DecompressorFactory{
    @Override
    public DictionaryReader createDictionaryReader(SkFile file) {
        return new DictionaryReaderLevelOne(file);
    }

    @Override
    public Decompressor createDecompressor(SkFile file) {
        return new DecompressorLevelOne(file);
    }
}
