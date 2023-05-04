package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;
import java.io.File;

public interface DecompressorFactory {
    DictionaryReader createDictionaryReader(SkFile file);

    Decompressor createDecompressor(SkFile file, File outfile);

}
