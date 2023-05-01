package com.example.decompressor;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;

public class SkDecomp extends Thread{
        private SkFile file;
        private TextArea messageField;
        public SkDecomp(SkFile file,TextArea messageField){
            this.file=file;
            this.messageField=messageField;
        }
        @Override
        public void run(){
            try {
               //file.setAllBytes(Files.readAllBytes(file.toPath()));
                 //messageField.setText(Byte.toString(file.getAllBytes()[0]));
                //messageField.setText(Character.toString(file.getAllBytes()[0]));
                SkFileReader skReader= new SkFileReader(file);
               this.interrupt();
                throw new InterruptedException();
                //SHUT UP.
            }
            catch(IOException ex){
                messageField.setText("An error has occurred while trying to open the selected file: "+ex.getMessage());
                return;
            }
            catch(InterruptedException ex){
                return;
            }
        }
}
