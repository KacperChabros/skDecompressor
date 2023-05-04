package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;
import java.io.File;

public interface DecompressorFactory {
    public DictionaryReader createDictionaryReader(SkFile file);

    public Decompressor createDecompressor(SkFile file, File outfile);

}
