package com.example.skdecomp;

import com.example.skdecomp.decompressor.DictionaryTrie;
import com.example.skdecomp.decompressor.TrieNode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BTVisualizer{
    private AnchorPane canvas;
    private DictionaryTrie dictionary;

    private int maxDepthLevel;
    private double canvasWidth;
    private double canvasHeight;
    public BTVisualizer(AnchorPane canvas, DictionaryTrie dictionary) {
        this.canvas=canvas;
        this.dictionary=dictionary;
        this.maxDepthLevel = dictionary.getLongestCodeLength();
        //System.out.println(maxDepthLevel);
        this.canvasWidth = 500000;
        //this.canvasWidth=10+(dictionary.getNumberOfSymbols()*(120))+ ((dictionary.getNumberOfSymbols()-1)*120)+10;//10 - padding.First is diameter of nodes. Second is horizontal padding between two nodes.
        this.canvasHeight=10+60*(dictionary.getLongestCodeLength()+1)+ dictionary.getLongestCodeLength()*60+10;//10 - padding top. Then is diameter of nodes. Then vertical padding between nodes.
        //canvas.setMaxWidth(canvasWidth);
        //canvas.setPrefWidth(canvasWidth);
        canvas.setMaxHeight(canvasHeight);
        canvas.setPrefHeight(canvasHeight);
        //canvas.getChildren().clear();
        //System.out.println(canvas.getWidth() + "|" + canvasWidth);
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
        //System.out.println(dictionary.getNumberOfSymbols());
        drawRecursive(canvasWidth/2,40,dictionary.getRoot(),0);

    }
    public void drawRecursive(double x, double y, TrieNode node,int currentDepthLevel){
        /*if(dictionary.getNumberOfSymbols()>=60){
            currentDepthLevel=currentDepthLevel+3;
        }else {
            currentDepthLevel++;
        }*/

        double currentPadding = (1000 * (maxDepthLevel - currentDepthLevel) + Math.pow(2, (maxDepthLevel - currentDepthLevel))) / Math.pow(2, currentDepthLevel-1);
        //double currentPadding = 20 * Math.pow(2, maxDepthLevel) / Math.pow(2, currentDepthLevel);
        //double currentPadding=canvasWidth/(2*(currentDepthLevel*currentDepthLevel));
        Circle circle = new Circle(x,y,30);
        circle.setFill(null);
        circle.setStroke((Paint.valueOf("black")));
        currentDepthLevel++;
        this.canvas.getChildren().add(circle);
        if(node.getChildren().containsKey('0'))
        {
            Line lineLeft = new Line(x,y+30,x-currentPadding-30,y+90);
            Text textLeft = new Text("0");
            textLeft.setFont(new Font("Verdana", 20));
            textLeft.setX( ( x + ( x - currentPadding - 60 ) ) / 2);
            textLeft.setY( ( (y + 30) + (y + 70) ) / 2 );
            this.canvas.getChildren().add(lineLeft);
            this.canvas.getChildren().add(textLeft);
            drawRecursive(x-currentPadding-30,y+120,node.getChildren().get('0'),currentDepthLevel);
        }
        if(node.getChildren().containsKey('1'))
        {
            Line lineRight = new Line(x,y+30,x+currentPadding+30,y+90);
            Text textRight = new Text("1");
            textRight.setFont(new Font("Verdana", 20));
            textRight.setX( ( x + ( x + currentPadding + 60 ) ) / 2 );
            textRight.setY( ( (y + 30) + (y + 70) ) / 2 );
            this.canvas.getChildren().add(lineRight);
            this.canvas.getChildren().add(textRight);
            drawRecursive(x+currentPadding+30,y+120,node.getChildren().get('1'),currentDepthLevel);
        }
        if(node.getEndOfWord())
        {
            Text symbolText = new Text("\'"+node.getSymbol() + "\'("+(int)node.getSymbol().charValue()+")");
            symbolText.setX( x - 20);
            symbolText.setY( y + 5);
            this.canvas.getChildren().add(symbolText);
        }
    }


}
