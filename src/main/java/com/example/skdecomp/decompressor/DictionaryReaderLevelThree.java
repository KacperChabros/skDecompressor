package com.example.skdecomp.decompressor;

import com.example.skdecomp.SkFile;

public class DictionaryReaderLevelThree implements DictionaryReader{
    private SkFile file;
    private DictionaryTrie dictionaryTrie;
    public DictionaryReaderLevelThree(SkFile file){
        this.file = file;
    }
    @Override
    public DictionaryTrie readDictionary() {
        //<editor-fold desc="Setting up local variables">
        int neededToSymbol = 16;
        int neededToBitLength = 8;
        int neededToCode = 0;
        dictionaryTrie = new DictionaryTrie();
        byte[] allBytes = file.getAllBytes();
        byte currentByte;
        int mask;
        int currentImportantBits = 8;
        byte status = 1;                    //status defines whether we are looking for a symbol {1}, a bitLength {2}
                                            // or a Code {3} to make a dictionary
        char currentSymbol = 0;
        char currentBitLength = 0;
        String currentCode = "";
        //</editor-fold>

        for(int i=8; i<(file.getDictLength()+8); i++)
        {
            currentByte = allBytes[i];
            mask = 0x00000080;                          //mask to read the dictionary bit by bit

            if(i == (file.getDictLength() + 8 - 1))     //if its last byte of dictionary -> read only important bits
            {
                currentImportantBits = file.getImportantBitsOfLastDictionaryByte();
            }

            for(int j=0; j<currentImportantBits; j++)
            {
                byte isCurrentBitSet = 0;
                if( (currentByte & mask ) != 0)         //check if currentBit is set
                {
                    isCurrentBitSet = 1;
                }
                switch (status)
                {
                    case 1:
                        neededToSymbol--;
                        currentSymbol <<= 1;
                        currentSymbol += isCurrentBitSet;

                        if(neededToSymbol == 0)      //if there is no neededToSymbol bits left -> go to reading bitLength
                        {
                            status = 2;
                            neededToSymbol = 16;
                        }
                        break;
                    case 2:
                        neededToBitLength--;
                        currentBitLength <<= 1;
                        currentBitLength += isCurrentBitSet;

                        if(neededToBitLength == 0) //if there is no neededToBitLength bits left -> go to reading Code
                        {
                            status = 3;
                            neededToBitLength = 8;
                            neededToCode = currentBitLength;
                        }
                        break;
                    case 3:
                        neededToCode--;
                        currentCode += isCurrentBitSet == 1 ? '1' : '0';
                        if(neededToCode == 0) //if there is no neededToCode bits left -> go back to reading Symbol
                        {
                            dictionaryTrie.insert(currentCode, currentSymbol);      //add to dictionary
                            status = 1;
                            currentSymbol = 0;
                            currentBitLength = 0;
                            currentCode = "";
                        }
                        break;
                }
                mask >>>= 1;                //pushing bits of mask to the right to check if the desired bit is checked
            }
        }
        return this.dictionaryTrie;
    }
}
