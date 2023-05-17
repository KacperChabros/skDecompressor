package com.example.skdecomp;

import com.example.skdecomp.decompressor.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;

public class SkDecomp implements Runnable{
        private SkFile file;
        private File outfile;
        private TextArea messageField;
        private AnchorPane canvas;
        private MyThreadListener listener;

        public SkDecomp(SkFile file, File outfile, TextArea messageField, AnchorPane canvas, MyThreadListener listener){
            this.file=file;
            this.messageField=messageField;
            this.outfile = outfile;
            this.canvas=canvas;
            this.listener = listener;
        }
        @Override
        public void run(){
            Messenger messenger;
            try {
                SkFileReader skReader= new SkFileReader(file);      //reading and validating the file
                skReader.readFile();
                file = skReader.getFile();                          //assigning file with assigned header values and
                                                                    //decyphered bytes
                DecompressorFactory factory = null;
                switch(file.getCompressLevel())
                {
                    case 0:
                        factory = new DecompressorLevel0Factory();
                        break;
                    case 1:
                        factory = new DecompressorLevel1Factory();
                        break;
                    case 2:
                        factory = new DecompressorLevel2Factory();
                        break;
                    case 3:
                        factory = new DecompressorLevel3Factory();
                        break;
                }
                file.setDictionary( factory.createDictionaryReader(file).readDictionary());
                factory.createDecompressor(file, outfile).decompress();
                messenger = new MessengerSuccess(messageField, "Done!");
                listener.threadFinished(file);
                /*if(file.getCompressLevel()==1)
                {
                    //BTVisualizer btVisualizer=new BTVisualizer(canvas,file.getDictionary());
                    //btVisualizer.visualize();
                    //Circle circle = new Circle(1300,40,30);
                    //canvas.getChildren().add(circle);
                }*/
                //this.interrupt();
                throw new InterruptedException();
                //SHUT UP.
            }
            catch(InvalidFileException ex)
            {
                messenger = new MessengerError(messageField, "File error: "+ex.getMessage());
                outfile.delete();
            }
            catch(IOException ex){
                String  message = "An error has occurred while trying to open the selected file: "+ex.getMessage();
                messenger = new MessengerError(messageField, message);
                outfile.delete();
                return;
            }
            catch(InterruptedException ex){
                return;
            }

        }

    public SkFile getFile() {
        return file;
    }
}
