package com.example.skdecomp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    //TODO: cypheredFile

    private static SkFile shorterThan8Bytes;
    private static SkFile withoutSK;
    private static SkFile corruptedFile;
    private static SkFile notCypheredTextFile;
    private static SkFile notCypheredImageFile;
    private static Validator validator;
    @BeforeAll
    public static void setupAll()
    {
        shorterThan8Bytes = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\shortFile.txt");
        withoutSK = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\brokenbigFile8WithoutSK.txt");
        corruptedFile = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\corruptedbigFile8.txt");
        notCypheredTextFile = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\outfile8");
        notCypheredImageFile = new SkFile("src\\test\\testFiles\\outfilesNotCyphered\\mountains8.jpg");
        try {
            shorterThan8Bytes.setAllBytes(Files.readAllBytes(shorterThan8Bytes.toPath()));
            withoutSK.setAllBytes(Files.readAllBytes(withoutSK.toPath()));
            corruptedFile.setAllBytes(Files.readAllBytes(corruptedFile.toPath()));
            notCypheredTextFile.setAllBytes(Files.readAllBytes(notCypheredTextFile.toPath()));
            notCypheredImageFile.setAllBytes(Files.readAllBytes(notCypheredImageFile.toPath()));
        } catch (IOException e) {
            System.out.println("Could not open some files");
        }
    }

    @Test
    @DisplayName("File shorther than size of header should throw InvalidFileException")
    public void fileShortherThan8BytesShouldThrowInvalidFileException()
    {
        validator = new Validator(shorterThan8Bytes.getAllBytes());

        assertThrows(InvalidFileException.class, () -> {
            validator.validate();
        });
    }

    @Test
    @DisplayName("File without \"SK\" in the beginning should throw InvalidFileException")
    public void fileWithoutInitialsShouldThrowInvalidFileException()
    {
        validator = new Validator(withoutSK.getAllBytes());

        assertThrows(InvalidFileException.class, () -> {
            validator.validate();
        });
    }

    @Test
    @DisplayName("Corrupted file (wrong checksum) should throw InvalidFileException")
    public void corruptedFileShouldThrowInvalidFileException()
    {
        validator = new Validator(corruptedFile.getAllBytes());

        assertThrows(InvalidFileException.class, () -> {
            validator.validate();
        });
    }

    @Test
    @DisplayName("Valid text file should be valid")
    public void notCypheredTextShouldBeValid() throws InvalidFileException {
        validator = new Validator(notCypheredTextFile.getAllBytes());
        validator.validate();
        assertEquals(true, validator.IsValid());
    }

    @Test
    @DisplayName("Valid image file should be valid")
    public void notCypheredImageShouldBeValid() throws InvalidFileException {
        validator = new Validator(notCypheredImageFile.getAllBytes());
        validator.validate();
        assertEquals(true, validator.IsValid());
    }
}