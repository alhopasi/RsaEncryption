# User Guide

## How to use the program
To be able to use the program, you have to have keys loaded.

## Main menu
You have several command options to use:
- 'g' - generate new keys
- 'e' - encrypt text
- 'd' - decrypt text
- 's' - save keys
- 'l' - load keys

### Generating new keys
- Enter 'g' as input and the program will generate new keys and load them.

### Encrypting text
- If you have a public key loaded, enter a text and the program will use the public key to encrypt it.
- The program will print out the encrypted text in hex.

### Decrypting text
- If you have a private key loaded, enter a text that has been encrypted with this program.
- The program will try to decrypt the text using the loaded private key.
- The program will print the decrypted text.

### Saving keys
- If you have both keys loaded, you can save the keys to a file.
- The files saved will be under the project path in "keys" folder.
- Enter a filename consisting of characters.

### Loading keys
- You can load a key to program by entering it's name, ex. cat.public or cat.private.
- You can load both keys by entering just the key name, ex. cat.
- Keys must be located in "keys" folder, under the project path.


Have fun!
