package com.example.decompressor;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;

public class SkDecomp implements Runnable{
        private SkFile file;
        private TextArea messageField;
        public SkDecomp(SkFile file,TextArea messageField){
            this.file=file;
            this.messageField=messageField;
        }
        @Override
        public void run(){
            try {
                SkFileReader skReader= new SkFileReader(file);
                skReader.readFile();
                //this.interrupt();
                throw new InterruptedException();
                //SHUT UP.
            }
            catch(InvalidFileException ex)
            {
                Messenger messenger = new MessengerError(messageField, "File error: "+ex.getMessage());
            }
            catch(IOException ex){
                String  message = "An error has occurred while trying to open the selected file: "+ex.getMessage();
                Messenger messenger = new MessengerError(messageField, message);
                return;
            }
            catch(InterruptedException ex){
                return;
            }
        }
}
