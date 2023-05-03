package com.example.decompressor;

public interface DecompressorFactory {
    public DictionaryReader createDictionaryReader(SkFile file);

    public Decompressor createDecompressor(SkFile file);
}
