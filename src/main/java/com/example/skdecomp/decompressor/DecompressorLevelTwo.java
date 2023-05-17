package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecompressorLevelTwo implements Decompressor{
    private SkFile file;
    private File outfile;
    public DecompressorLevelTwo(SkFile file, File outfile){
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
        char fullChar = 0;
        char halfChar = 0;
        int writtenBytes = 0;
        FileOutputStream outputStream = new FileOutputStream(outfile, true);
        //</editor-fold>

        for(int i = 8 + file.getDictLength(); i < (allBytes.length - file.getNumberOfNotCompressedBytes()); i++)
        {
            byte currentByte = allBytes[i];
            int mask = 0x00000080;                          //mask to read the dictionary bit by bit

            if(i == (allBytes.length - file.getNumberOfNotCompressedBytes() - 1))  //checking if its last compressed byte
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

                currentCode += isCurrentBitSet == 1 ? '1' : '0';        //appending to current code value of bit

                if((foundSymbol = file.getDictionary().lookForSymbol(currentCode)) != null)
                {
                    fullChar = 0;                       //foundSymbol is 12 bit, written symbol is 8 bit
                    writtenBytes++;                     //fullChar is 8 bits extracted from foundSymbol
                                                        //halfChar is 4 bits extracted from foundSymbol
                                                        //fullChar can be written, halfChar should be shifted by 4
                                                        // and get 4 bits from the next symbol and be written then
                    if(writtenBytes % 2 != 0)
                    {
                        halfChar += (foundSymbol & 0x000f);
                        halfChar = (char) (halfChar << 4);
                        foundSymbol = (char) (foundSymbol >>> 4);
                        fullChar += foundSymbol;
                        outputStream.write( (byte)(fullChar & 0x00ff) );    //writing symbol
                    }
                    else
                    {
                        fullChar += (foundSymbol & 0x00ff);
                        foundSymbol = (char)(foundSymbol >>>8);
                        halfChar += foundSymbol;
                        outputStream.write( (byte)(halfChar & 0x00ff) );        //writing symbols
                        outputStream.write( (byte)(fullChar & 0x00ff) );
                        halfChar = 0;
                    }
                    currentCode = "";
                }
                mask >>>= 1;                //changing the mask to check the next bit
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
