package com.example.skdecomp.decompressor;

import com.example.skdecomp.InvalidFileException;
import com.example.skdecomp.SkFile;
import com.example.skdecomp.SkFileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;

class DictionaryReaderLevelZeroTest {

    private static SkFile levelZero;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        levelZero = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile0");


        SkFileReader fileReader = new SkFileReader(levelZero);
        fileReader.readFile();
        levelZero = fileReader.getFile();
        DictionaryReader dictionaryReader = new DictionaryReaderLevelZero(levelZero);
        levelZero.setDictionary(dictionaryReader.readDictionary());
    }

    @Test
    @DisplayName("Dictionary of compress level 0 should be null")
    public void dictionaryOfCL0ShouldBeNull()
    {
        assertNull(levelZero.getDictionary());
    }
}