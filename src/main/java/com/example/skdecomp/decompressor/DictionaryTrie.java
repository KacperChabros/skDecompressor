package com.example.skdecomp.decompressor;

public class DictionaryTrie {
    private TrieNode root;
    private int numberOfSymbols;
    private int longestCodeLength;
    public DictionaryTrie()
    {
        this.root = new TrieNode();
        this.numberOfSymbols=0;
        this.longestCodeLength =0;
    }

    public void insert(String code, char symbol)
    {
        TrieNode current = root;

        for(char c : code.toCharArray())
        {
            if(!current.getChildren().containsKey(c))
                current.getChildren().put(c, new TrieNode());
            current = current.getChildren().get(c);
        }
        current.setEndOfWord(true);
        current.setSymbol(symbol);
        if(code.length()>=this.longestCodeLength)
        {
            this.longestCodeLength =code.length();
        }
        this.numberOfSymbols++;
    }

    public Character lookForSymbol(String code){
        TrieNode current = root;
        for(char c : code.toCharArray())
        {
            if(!current.getChildren().containsKey(c))
                return null;
            current = current.getChildren().get(c);
        }
        if(current.getEndOfWord())
        {
            return current.getSymbol();
        }else
        {
            return null;
        }
    }
    public int getNumberOfSymbols() {
        return this.numberOfSymbols;
    }
    public int getLongestCodeLength() {
        return this.longestCodeLength;
    }

    public TrieNode getRoot() {
        return root;
    }
}
