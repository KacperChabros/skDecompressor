package com.example.skdecomp;

import com.example.skdecomp.decompressor.DictionaryTrie;
import com.example.skdecomp.decompressor.TrieNode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class BTVisualizer{
    private AnchorPane canvas;
    private DictionaryTrie dictionary;
    private double canvasWidth;
    private double canvasHeight;
    public BTVisualizer(AnchorPane canvas, DictionaryTrie dictionary) {
        this.canvas=canvas;
        this.dictionary=dictionary;
        this.canvasWidth=10+(dictionary.getNumberOfSymbols()*(120))+ ((dictionary.getNumberOfSymbols()-1)*120)+10;//10 - padding.First is diameter of nodes. Second is horizontal padding between two nodes.
        this.canvasHeight=10+60*(dictionary.getLongestCodeLength()+1)+ dictionary.getLongestCodeLength()*60+10;//10 - padding top. Then is diameter of nodes. Then vertical padding between nodes.
        canvas.setMaxWidth(canvasWidth);
        canvas.setPrefWidth(canvasWidth);
        canvas.setMaxHeight(canvasHeight);
        canvas.setPrefHeight(canvasHeight);
        canvas.getChildren().clear();

    }
    /*@Override
    public void run() {
        visualize();
    }*/
    public void visualize(){
        /*
        Circle root = new Circle(canvasWidth /2,40,30);
        root.setFill(null);
        root.setStroke(Paint.valueOf("black"));
        this.canvas.getChildren().add(root);*/
        System.out.println(dictionary.getNumberOfSymbols());
        drawRecursive(canvas.getWidth()/2,40,dictionary.getRoot(),0);

    }
    public void drawRecursive(double x, double y, TrieNode node,int currentDepthLevel){
        if(dictionary.getNumberOfSymbols()>=60){
            currentDepthLevel=currentDepthLevel+3;
        }else {
            currentDepthLevel++;
        }
        double currentPadding=canvasWidth/(2*(currentDepthLevel*currentDepthLevel));
        Circle circle = new Circle(x,y,30);
        circle.setFill(null);
        circle.setStroke((Paint.valueOf("black")));
        this.canvas.getChildren().add(circle);
        if(node.getChildren().containsKey('0'))
        {
            Line lineLeft = new Line(x,y+30,x-currentPadding,y+90);
            this.canvas.getChildren().add(lineLeft);
            drawRecursive(x-currentPadding,y+120,node.getChildren().get('0'),currentDepthLevel);
        }
        if(node.getChildren().containsKey('1'))
        {
            Line lineRight = new Line(x,y+30,x+currentPadding,y+90);
            this.canvas.getChildren().add(lineRight);
            drawRecursive(x+currentPadding,y+120,node.getChildren().get('1'),currentDepthLevel);
        }
    }


}
