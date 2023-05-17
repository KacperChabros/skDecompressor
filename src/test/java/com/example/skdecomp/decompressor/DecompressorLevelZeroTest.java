package com.example.skdecomp.decompressor;

import com.example.skdecomp.InvalidFileException;
import com.example.skdecomp.SkFile;
import com.example.skdecomp.SkFileReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DecompressorLevelZeroTest {
    private static SkFile levelZeroText;
    private static  SkFile levelZeroImage;
    private static File outfileText;
    private static File outfileImage;
    private static File notCompressedText;
    private static File notCompressedImage;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        //Text file
        levelZeroText = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile0");
        outfileText = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompressed0");
        outfileText.createNewFile();
        notCompressedText = new File("src\\test\\testFiles\\notCompressed\\infile");


        SkFileReader fileReader = new SkFileReader(levelZeroText);
        fileReader.readFile();
        levelZeroText = fileReader.getFile();
        DictionaryReader dictionaryReader = new DictionaryReaderLevelZero(levelZeroText);
        levelZeroText.setDictionary(dictionaryReader.readDictionary());

        DecompressorLevelZero decompressor = new DecompressorLevelZero(levelZeroText, outfileText);
        decompressor.decompress();

        //Image file
        levelZeroImage = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\mountains0.jpg");
        outfileImage = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompMoun0.jpg");
        outfileImage.createNewFile();
        notCompressedImage = new File("src\\test\\testFiles\\notCompressed\\mountains.jpg");

        fileReader = new SkFileReader(levelZeroImage);
        fileReader.readFile();
        levelZeroImage = fileReader.getFile();
        dictionaryReader = new DictionaryReaderLevelZero(levelZeroImage);
        levelZeroImage.setDictionary(dictionaryReader.readDictionary());

        decompressor = new DecompressorLevelZero(levelZeroImage, outfileImage);
        decompressor.decompress();
    }

    @Test
    @DisplayName("Text file test")
    @Description("Check if the decompressed file equals the file before compression")
    public void decompressedAndNotCompressedTextFileShouldBeTheSame() throws IOException {
       assertArrayEquals(Files.readAllBytes(notCompressedText.toPath()), Files.readAllBytes(outfileText.toPath()));
    }

    @Test
    @DisplayName("Image file test")
    @Description("Check if the decompressed file equals the file before compression")
    public void decompressedAndNotCompressedImageFileShouldBeTheSame() throws IOException {
        assertArrayEquals(Files.readAllBytes(notCompressedImage.toPath()), Files.readAllBytes(outfileImage.toPath()));
    }

    @AfterAll
    public static void tearDownAll()
    {
        outfileText.delete();
        outfileImage.delete();
    }
}