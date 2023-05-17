package com.example.skdecomp.decompressor;

import com.example.skdecomp.InvalidFileException;
import com.example.skdecomp.SkFile;
import com.example.skdecomp.SkFileReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryReaderLevelTwoTest {
    private static SkFile levelTwo;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException {
        levelTwo = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile12");

        SkFileReader fileReader = new SkFileReader(levelTwo);
        fileReader.readFile();
        levelTwo = fileReader.getFile();

        DictionaryReaderLevelTwo dictionaryReader = new DictionaryReaderLevelTwo(levelTwo);
        levelTwo.setDictionary(dictionaryReader.readDictionary());
    }

    @Test
    @DisplayName("Shortest code test")
    @Description("Symbol of \"101\" should be represented by 1782")
    public void symbolOf101ShouldBeRepresentedBy1782()
    {
        assertEquals(1782, levelTwo.getDictionary().lookForSymbol("101").charValue());
    }

    @Test
    @DisplayName("Middle length of code test")
    @Description("Symbol of \"0101\" should be represented by 518")
    public void symbolOf0101ShouldBeRepresentedBy518()
    {
        assertEquals(518, levelTwo.getDictionary().lookForSymbol("0101").charValue());
    }

    @Test
    @DisplayName("Longest code test")
    @Description("Symbol of \"10010\" should be represented by 116")
    public void symbolOf10010ShouldBRepresentedBy116()
    {
        assertEquals(116, levelTwo.getDictionary().lookForSymbol("10010").charValue());
    }

    @Test
    @DisplayName("Last code test")
    @Description("Symbol of \"10001\" should be represented by code 103")
    public void symbolOf10001ShouldBeRepresentedBy10()
    {
        assertEquals(103, levelTwo.getDictionary().lookForSymbol("10001").charValue());
    }

}