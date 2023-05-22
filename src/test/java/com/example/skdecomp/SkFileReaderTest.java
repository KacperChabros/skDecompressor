package com.example.skdecomp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkFileReaderTest {
    private static SkFile notCypheredLevelZero;
    private static SkFile notCypheredLevelOne;
    private static SkFile notCypheredLevelTwo;
    private static SkFile notCypheredLevelThree;

    private static SkFile cypheredLevelTwo;

    private static SkFile notCypheredLevelThreeSmallFile;
    private static SkFileReader fileReader;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        notCypheredLevelZero = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\k0.png");
        notCypheredLevelOne = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\k8.png");
        notCypheredLevelTwo = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\k12.png");
        notCypheredLevelThree = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\k16.png");
        notCypheredLevelThreeSmallFile = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile16");
        cypheredLevelTwo = new SkFile("src\\test\\testFiles\\outfilesCyphered\\k12.png");
        cypheredLevelTwo.setPassword("haslo");

        fileReader = new SkFileReader(notCypheredLevelZero);
        fileReader.readFile();
        notCypheredLevelZero = fileReader.getFile();

        fileReader = new SkFileReader(notCypheredLevelOne);
        fileReader.readFile();
        notCypheredLevelOne = fileReader.getFile();

        fileReader = new SkFileReader(notCypheredLevelTwo);
        fileReader.readFile();
        notCypheredLevelTwo = fileReader.getFile();

        fileReader = new SkFileReader(notCypheredLevelThree);
        fileReader.readFile();
        notCypheredLevelThree = fileReader.getFile();

        fileReader = new SkFileReader(notCypheredLevelThreeSmallFile);
        fileReader.readFile();
        notCypheredLevelThreeSmallFile = fileReader.getFile();

        fileReader = new SkFileReader(cypheredLevelTwo);
        fileReader.readFile();
        cypheredLevelTwo = fileReader.getFile();

    }

    @Test
    @DisplayName("Test extraction of compress levels")
    public void assertCorrectCompressLevelExtraction()  {
        assertEquals(0, notCypheredLevelZero.getCompressLevel());
        assertEquals(1, notCypheredLevelOne.getCompressLevel());
        assertEquals(2, notCypheredLevelTwo.getCompressLevel());
        assertEquals(3, notCypheredLevelThree.getCompressLevel());
    }

    @Test
    @DisplayName("Test extraction of cyphered flag")
    public void assertCorrectCypheredExtraction() throws InvalidFileException, IOException {
        assertEquals(false, notCypheredLevelTwo.getCyphered());
        assertEquals(true, cypheredLevelTwo.getCyphered());

    }

    @Test
    @DisplayName("Test extraction of number of important bits in last compressed byte")
    public void assertCorrectImportantBitsInLastCompressedByte(){

        assertEquals(8, notCypheredLevelZero.getImportantBitsInLastCompressedByte());
        assertEquals(3, notCypheredLevelOne.getImportantBitsInLastCompressedByte());
        assertEquals(8, notCypheredLevelTwo.getImportantBitsInLastCompressedByte());
        assertEquals(2, notCypheredLevelThree.getImportantBitsInLastCompressedByte());

    }

    @Test
    @DisplayName("Test extraction of dictionary length")
    public void assertCorrectDictionaryLength() {

        assertEquals(0, notCypheredLevelZero.getDictLength());
        assertEquals(778, notCypheredLevelOne.getDictLength());
        assertEquals(16192, notCypheredLevelTwo.getDictLength());
        assertEquals(67348, notCypheredLevelThree.getDictLength());

    }

    @Test
    @DisplayName("Test extraction of number of important bits in the last dictionary byte")
    public void assertCorrectImportantBitsOfLastDictionaryByte() {

        assertEquals(0, notCypheredLevelZero.getImportantBitsOfLastDictionaryByte());
        assertEquals(3, notCypheredLevelOne.getImportantBitsOfLastDictionaryByte());
        assertEquals(5, notCypheredLevelTwo.getImportantBitsOfLastDictionaryByte());
        assertEquals(4, notCypheredLevelThree.getImportantBitsOfLastDictionaryByte());

    }

    @Test
    @DisplayName("Test extraction of number not compressed bytes")
    public void assertCorrectNotCompressedBytes() {

        assertEquals(0, notCypheredLevelZero.getNumberOfNotCompressedBytes());
        assertEquals(0, notCypheredLevelOne.getNumberOfNotCompressedBytes());
        assertEquals(2, notCypheredLevelTwo.getNumberOfNotCompressedBytes());
        assertEquals(1, notCypheredLevelThreeSmallFile.getNumberOfNotCompressedBytes());

    }
}