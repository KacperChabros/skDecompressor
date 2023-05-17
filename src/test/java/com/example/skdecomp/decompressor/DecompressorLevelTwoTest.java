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

class DecompressorLevelTwoTest {
    private static SkFile levelTwoText;
    private static  SkFile levelTwoImage;
    private static File outfileText;
    private static File outfileImage;
    private static File notCompressedText;
    private static File notCompressedImage;

    @BeforeAll
    public static void setupAll() throws IOException, InvalidFileException
    {
        //Text file
        levelTwoText = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile12");
        outfileText = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompressed12");
        outfileText.createNewFile();
        notCompressedText = new File("src\\test\\testFiles\\notCompressed\\infile");


        SkFileReader fileReader = new SkFileReader(levelTwoText);
        fileReader.readFile();
        levelTwoText = fileReader.getFile();
        DictionaryReader dictionaryReader = new DictionaryReaderLevelTwo(levelTwoText);
        levelTwoText.setDictionary(dictionaryReader.readDictionary());

        DecompressorLevelTwo decompressor = new DecompressorLevelTwo(levelTwoText, outfileText);
        decompressor.decompress();

        //Image file
        levelTwoImage = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\mountains12.jpg");
        outfileImage = new File("src\\test\\testFiles\\outfilesNotCyphered\\decompMoun12.jpg");
        outfileImage.createNewFile();
        notCompressedImage = new File("src\\test\\testFiles\\notCompressed\\mountains.jpg");

        fileReader = new SkFileReader(levelTwoImage);
        fileReader.readFile();
        levelTwoImage = fileReader.getFile();
        dictionaryReader = new DictionaryReaderLevelTwo(levelTwoImage);
        levelTwoImage.setDictionary(dictionaryReader.readDictionary());

        decompressor = new DecompressorLevelTwo(levelTwoImage, outfileImage);
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