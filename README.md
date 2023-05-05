# skDecompressor
"Skdecomp" is a decompressor for files compressed with Skcomp which implements Huffman's algorithm. Skdecomp was created with my friend as a university project. It allows user to select a file and decompress it if it's valid. The program extracts the needed information from file so it is not necessary to provide such information. Skdecomp provides simple GUI and visualizing a tree for 8-bit compression (work in progress...).

## Technologies
* Java
* JavaFX
### Design Patterns
* Abstract Factory

## Features
* decompress file with one out of 4 compression levels
  * Level 0 - no compression
  * Level 1 - 8-bit compression
  * Level 2 - 12-bit compression
  * Level 3 - 16-bit compression

* check if the file is valid using checksum
* Graphical User Interface
### Features to be added
* decypher file with desired password using XOR algorithm
* Visualize binary tree for 8-bit compression

## Setup and usage
One can clone the project into Intellij IDE  
  
  * File should be selected with using button  
  * User should provide name of the decompressed file (the file will be created in the same location as compressed file)  
  * To decompress the file one should click the appropriate button  
  * To display help one should click the appropriate button  
  * After successful decompression there will be opportunity to visualize the result of Huffman's algorithm - binary tree. Note: it is only possible for Level 1 Compression  
## Credits
I created this project with:
* [Grazzly](https://github.com/Grazzly)
