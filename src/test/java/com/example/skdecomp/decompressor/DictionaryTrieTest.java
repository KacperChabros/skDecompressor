package com.example.skdecomp.decompressor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTrieTest {
    private static DictionaryTrie dictionaryTrie;
    @BeforeAll
    public static void setupAll()
    {
        dictionaryTrie = new DictionaryTrie();
        dictionaryTrie.insert("00", 'A');
        dictionaryTrie.insert("010", 'B');
        dictionaryTrie.insert("0110", 'C');
        dictionaryTrie.insert("0111", 'D');
        dictionaryTrie.insert("1", 'E');
    }

    /*
        for the future
    @Test
    @DisplayName("Number of symbols test")
    public void numberOfSymbolsShouldBe5()
    {
        assertEquals(5, dictionaryTrie.getNumberOfSymbols());
    }

    @Test
    @DisplayName("Longest code length test")
    public void longestCodeLengthShouldBe4()
    {
        assertEquals(4, dictionaryTrie.getLongestCodeLength());
    }*/

    @Test
    @DisplayName("Symbol with code \"00\" is A")
    public void symbolOf00ShouldBeA()
    {
        assertEquals('A', dictionaryTrie.lookForSymbol("00"));
    }

    @Test
    @DisplayName("Symbol with code \"1\" is E")
    public void symbolOf1ShouldBeE()
    {
        assertEquals('E', dictionaryTrie.lookForSymbol("1"));
    }

    @Test
    @DisplayName("Symbol with code \"0110\" is C")
    public void symbolOf0110ShouldBeA()
    {
        assertEquals('C', dictionaryTrie.lookForSymbol("0110"));
    }
}