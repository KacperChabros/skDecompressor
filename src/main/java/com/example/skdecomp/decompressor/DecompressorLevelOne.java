package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

import java.io.*;

public class DecompressorLevelOne implements Decompressor{
    private SkFile file;
    private File outfile;
    public DecompressorLevelOne(SkFile file, File outfile){
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

        for(int i = 8 + file.getDictLength(); i < allBytes.length; i++)
        {
            byte currentByte = allBytes[i];
            int mask = 0x00000080;                          //mask to read the dictionary bit by bit

            if(i == (allBytes.length - file.getNumberOfNotCompressedBytes() - 1)) //checking if its last compressed byte
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
                    outputStream.write( (byte)(foundSymbol & 0x00ff) ); //writing symbol
                    currentCode = "";
                }
                mask >>>= 1;                //changing the mask to check the next bit
            }
        }
       outputStream.close();
    }
}
