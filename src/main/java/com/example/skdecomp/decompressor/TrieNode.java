package com.example.skdecomp.decompressor;

import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;
    private Character symbol;

    public void setEndOfWord(boolean value){
        this.isEndOfWord = value;
    }
    public boolean getEndOfWord(){
        return this.isEndOfWord;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }
    public Character getSymbol(){
        return this.symbol;
    }

    public HashMap<Character, TrieNode> getChildren(){
        return this.children;
    }

    public void setChildren(HashMap<Character, TrieNode> children){
        this.children = children;
    }
}
