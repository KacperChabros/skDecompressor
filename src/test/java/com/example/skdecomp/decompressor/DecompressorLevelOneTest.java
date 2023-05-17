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

class DecompressorLevelOneTest {
    private static SkFile levelOneText;
    private static  SkFile levelOneImage;
    private static File outfileText;
    private static File outfileImage;
    private static File notCompressedText;
    private static File notCompressedImage;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        //Text file
        levelOneText = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile8");
        outfileText = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompressed8");
        outfileText.createNewFile();
        notCompressedText = new File("src\\test\\testFiles\\notCompressed\\infile");


        SkFileReader fileReader = new SkFileReader(levelOneText);
        fileReader.readFile();
        levelOneText = fileReader.getFile();
        DictionaryReader dictionaryReader = new DictionaryReaderLevelOne(levelOneText);
        levelOneText.setDictionary(dictionaryReader.readDictionary());

        DecompressorLevelOne decompressor = new DecompressorLevelOne(levelOneText, outfileText);
        decompressor.decompress();

        //Image file
        levelOneImage = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\mountains8.jpg");
        outfileImage = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompMoun8.jpg");
        outfileImage.createNewFile();
        notCompressedImage = new File("src\\test\\testFiles\\notCompressed\\mountains.jpg");

        fileReader = new SkFileReader(levelOneImage);
        fileReader.readFile();
        levelOneImage = fileReader.getFile();
        dictionaryReader = new DictionaryReaderLevelOne(levelOneImage);
        levelOneImage.setDictionary(dictionaryReader.readDictionary());

        decompressor = new DecompressorLevelOne(levelOneImage, outfileImage);
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