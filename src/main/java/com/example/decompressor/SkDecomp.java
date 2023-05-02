package com.example.decompressor;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;

public class SkDecomp extends Thread{
        private SkFile file;
        //private Messenger messenger;
        private TextArea messageField;
        public SkDecomp(SkFile file,TextArea messageField){
            this.file=file;
            //this.messenger=new Messenger();
            this.messageField=messageField;
        }
        @Override
        public void run(){
            try {
                SkFileReader skReader= new SkFileReader(file);
                skReader.readFile();
               this.interrupt();
                throw new InterruptedException();
                //SHUT UP.
            }
            catch(InvalidFileException ex)
            {
                messageField.setText("File error: "+ex.getMessage());
            }
            catch(IOException ex){
                messageField.setText("An error has occurred while trying to open the selected file: "+ex.getMessage());
                //messenger.displayMessage("An error has occurred while trying to open the selected file:"+ex.getMessage());
                return;
            }
            catch(InterruptedException ex){
                return;
            }
        }
}
