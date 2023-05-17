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

class DictionaryReaderLevelOneTest {

    private static SkFile levelOne;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException {
        levelOne = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile8");

        SkFileReader fileReader = new SkFileReader(levelOne);
        fileReader.readFile();
        levelOne = fileReader.getFile();

        DictionaryReaderLevelOne dictionaryReader = new DictionaryReaderLevelOne(levelOne);
        levelOne.setDictionary(dictionaryReader.readDictionary());
    }

    @Test
    @DisplayName("Shortest code test")
    @Description("Symbol of \"00\" should be space")
    public void symbolOf00ShouldBeSpace()
    {
        assertEquals(' ', levelOne.getDictionary().lookForSymbol("00"));
    }

    @Test
    @DisplayName("Middle length of code test")
    @Description("Symbol of \"0110\" should be m")
    public void symbolOf0110ShouldBem()
    {
        assertEquals('m', levelOne.getDictionary().lookForSymbol("0110"));
    }

    @Test
    @DisplayName("Longest code test")
    @Description("Symbol of \"111111\" should be w")
    public void symbolOf111111ShouldBew()
    {
        assertEquals('w', levelOne.getDictionary().lookForSymbol("111111"));
    }

    @Test
    @DisplayName("Last code test")
    @Description("Symbol of \"10000\" should be ascii code 10")
    public void symbolOf10000ShouldBeAscii10()
    {
        assertEquals(10, levelOne.getDictionary().lookForSymbol("10000").charValue());
    }
}