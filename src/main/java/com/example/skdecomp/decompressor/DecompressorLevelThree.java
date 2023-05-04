package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecompressorLevelThree implements Decompressor{
    private SkFile file;
    private File outfile;
    public DecompressorLevelThree(SkFile file, File outfile){
        this.file = file;
        this.outfile = outfile;
    }
    @Override
    public void decompress() throws IOException{
        //<editor-fold desc="Setting up variables">
        byte[] allBytes = file.getAllBytes();
        int currentImportantBits = 8;
        String currentCode = "";
        Character foundSymbol;
        FileOutputStream outputStream = new FileOutputStream(outfile, true);
        //</editor-fold>
        for(int i = 8 + file.getDictLength(); i < (allBytes.length - file.getNumberOfNotCompressedBytes()); i++)
        {
            byte currentByte = allBytes[i];
            int mask = 0x00000080;                          //mask to read the dictionary bit by bit

            if(i == (allBytes.length - file.getNumberOfNotCompressedBytes() - 1))   //checking if its last compressed byte
            {
                currentImportantBits = file.getImportantBitsInLastCompressedByte();
            }

            for(int j=0; j<currentImportantBits; j++)
            {
                byte isCurrentBitSet = 0;
                if( (currentByte & mask) != 0)          //checking if current bit is set
                {
                    isCurrentBitSet = 1;
                }

                currentCode += isCurrentBitSet == 1 ? '1' : '0';    //appending to current code value of bit
                if((foundSymbol = file.getDictionary().lookForSymbol(currentCode)) != null)
                {
                    char firstHalf = 0;                 //foundSymbol is 16 bit, written symbol is 8 bit
                    char secondHalf = 0;                //so we divide it in half
                    secondHalf += (foundSymbol & 0x00ff);
                    foundSymbol = (char) (foundSymbol>>>8);
                    firstHalf += (foundSymbol & 0x00ff);

                    outputStream.write( (byte)(firstHalf & 0x00ff) );   //writing symbols
                    outputStream.write( (byte)(secondHalf & 0x00ff) );
                    currentCode = "";
                }
                mask >>>= 1;                        //changing the mask to check the next bit
            }
        }
        //taking care of not compressed bytes
        for(int i = allBytes.length - file.getNumberOfNotCompressedBytes(); i<allBytes.length; i++)
        {
            outputStream.write( allBytes[i] );
        }
       outputStream.close();
    }
}
