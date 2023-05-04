package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

public class DictionaryReaderLevelZero implements DictionaryReader{
    private SkFile file;
    private DictionaryTrie dictionaryTrie;
    public DictionaryReaderLevelZero(SkFile file){
        this.file = file;
    }
    @Override
    public DictionaryTrie readDictionary() {
        //there is no dictionary in level zero -> returns null
        return null;
    }
}
