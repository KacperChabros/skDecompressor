package com.example.skdecomp.decompressor;

public class DictionaryTrie {
    private TrieNode root;

    public DictionaryTrie()
    {
        this.root = new TrieNode();
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
}
