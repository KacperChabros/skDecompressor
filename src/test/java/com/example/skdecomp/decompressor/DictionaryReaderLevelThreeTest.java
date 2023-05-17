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

class DictionaryReaderLevelThreeTest {
    private static SkFile levelThree;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException {
        levelThree = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile16");

        SkFileReader fileReader = new SkFileReader(levelThree);
        fileReader.readFile();
        levelThree = fileReader.getFile();

        DictionaryReaderLevelThree dictionaryReader = new DictionaryReaderLevelThree(levelThree);
        levelThree.setDictionary(dictionaryReader.readDictionary());
    }

    @Test
    @DisplayName("Shortest code test")
    @Description("Symbol of \"100\" should be represented by 25888")
    public void symbolOf100ShouldBeRepresentedBy25888()
    {
        assertEquals(25888, levelThree.getDictionary().lookForSymbol("100").charValue());
    }

    @Test
    @DisplayName("Middle length of code test")
    @Description("Symbol of \"0000\" should be represented by 21359")
    public void symbolOf0000ShouldBeRepresentedBy21359()
    {
        assertEquals(21359, levelThree.getDictionary().lookForSymbol("0000").charValue());
    }

    @Test
    @DisplayName("Longest code test")
    @Description("Symbol of \"11111\" should be represented by 27680")
    public void symbolOf11111ShouldBRepresentedBy27680()
    {
        assertEquals(27680, levelThree.getDictionary().lookForSymbol("11111").charValue());
    }

    @Test
    @DisplayName("Last code test")
    @Description("Symbol of \"0110\" should be represented by code 8295")
    public void symbolOf0110ShouldBeRepresentedBy8295()
    {
        assertEquals(8295, levelThree.getDictionary().lookForSymbol("0110").charValue());
    }

}