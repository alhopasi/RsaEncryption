# Project definition

The goal of the project is to create a working RSA-keypair generator and the possibility to use RSA-keys to encrypt and decrypt small texts.

## Algorithms and data structures

- A number, which can be as long as possible.
- For this number, possibility to use many operations.
- Random generator, which can randomize a byte array.
- A pair, which has objects.

## Problem

- Generating new RSA-keys: public and private.
- Encrypting ja decrypting text.
- Writing keys to a file and reading those from a file.

These are secondary objectives. To be done if there is enough time:
- Padding the plaintext before encrypting.
- Using a hash in the padding (SHA-256)
- Generating signature and adding it to a message.

## Input and output

- Choose if we want to generate a new RSA-keypair or use an existing key.
- If we use existing key, do we use private (decrypt) or public (encrypt) key.
- Name of the key
- Text to be encrypted

## Time and Space complexity

- Using RSA-encryption, the plaintext needs to be shorter than the length of the key. Time to encrypt the text depends on the length of the key, which is constant. The length of the input does not affect the time or space it takes to run the encryption and decryption.
- Time and space complexity are both O(n).

## Sources

- https://simple.wikipedia.org/wiki/RSA_algorithm
- https://fi.wikipedia.org/wiki/Modulaariaritmetiikan_käänteisluku
- https://security.stackexchange.com/questions/112029/should-sha-1-be-used-with-rsa-oaep
- https://en.wikipedia.org/wiki/SHA-2
- https://en.wikipedia.org/wiki/Exponentiation#Efficient_computation_with_integer_exponents