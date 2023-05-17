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

class DecompressorLevelThreeTest {
    private static SkFile levelThreeText;
    private static  SkFile levelThreeImage;
    private static File outfileText;
    private static File outfileImage;
    private static File notCompressedText;
    private static File notCompressedImage;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        //Text file
        levelThreeText = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile16");
        outfileText = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompressed16");
        outfileText.createNewFile();
        notCompressedText = new File("src\\test\\testFiles\\notCompressed\\infile");


        SkFileReader fileReader = new SkFileReader(levelThreeText);
        fileReader.readFile();
        levelThreeText = fileReader.getFile();
        DictionaryReader dictionaryReader = new DictionaryReaderLevelThree(levelThreeText);
        levelThreeText.setDictionary(dictionaryReader.readDictionary());

        DecompressorLevelThree decompressor = new DecompressorLevelThree(levelThreeText, outfileText);
        decompressor.decompress();

        //Image file
        levelThreeImage = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\mountains16.jpg");
        outfileImage = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompMoun16.jpg");
        outfileImage.createNewFile();
        notCompressedImage = new File("src\\test\\testFiles\\notCompressed\\mountains.jpg");

        fileReader = new SkFileReader(levelThreeImage);
        fileReader.readFile();
        levelThreeImage = fileReader.getFile();
        dictionaryReader = new DictionaryReaderLevelThree(levelThreeImage);
        levelThreeImage.setDictionary(dictionaryReader.readDictionary());

        decompressor = new DecompressorLevelThree(levelThreeImage, outfileImage);
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