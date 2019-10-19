# Implementation

In this document, we take an indepth look how the implementation of the program was made.

## RSA Key Generator

- RSA key generator uses the normal RSA key generation algorithm.
- Once we have two primes (p and q), we calculate n and d. e is some exponent that is decided beforehand (here 65537).
- n is p multiplied by q.
- d is calculated with Extended Euclidean Algorithm.
- What algorithms were used.

### Prime generation

- To generate a prime, we generate a random number of certain bit size.
- First we check with a small prime test if the possible prime is a modulo of those. If it is, we can skip it.
- The real prime check is done using Miller Rabin test. Miller Rabin is a probalistic test, testing for high possibility if it's a prime.
- If it's not a prime, increment by 2 and try again.

## Public key

- Public key consists of one number (n) of certain bit size, and an exponent e.

## Private key

- Private key consists of two numbers of certain bit size: d and n. d is the secret component that should never be revealed to anyone.

## Encoding text

Input is read by program. The input is converted into decimal. Decimal is then taken to power of public keys e (exponent). Then modulo of public keys n (prime). The result is then turned into hex string.
- Short, we take these steps, receiving input `text`:
`text -> decimal`
`result = decimal^e mod n`
`result -> hex`

## Decoding text

Text is first received as hex string. This hex is transformed into decimal. Decimal is then taken to power of private keys d (secret prime). Then modulo of private keys n (known prime). The result is then turned into readable string.
- In short, we take these steps, receiving input `hex`:
`hex -> decimal`.
`result = decimal^d mod n`
`result -> readable string`

## (My)BigInteger

- BigInteger is implemented as array of decimals, where each array has an int of 0-9. ex. [2,2,5] = 225. This way we can have integers as long as we want.
- Most calculations are written by me, taking inspiration from internet.

#### Basic calculations
- Add and Subtract are ran in O(n) time, going through the smaller numbers all indexes, adding or subtracting them to the bigger numbers correct indexes. Here n is the length of the smaller number. Note that subtract always subtracts the smaller number from the bigger number.
- Multiply runs in O(n\*k) time, where k is the sum of smaller numbers integers (ex. number 225, k = 2+2+5 = 9) and n is the length of the bigger number. It uses add to calculate each indexes sum and adds it to the result array. Multiply counts the sum of each index's number times bigger number, and then adds this to the result array correct indexes, and moves on to the next index.
- Divide works quite similarly as multiply, it counts how many times it can divide the bigger number by each number in smaller numbers index, and subtracts it from the original number, saving the result in result array, while carrying the modulo over to the bigger number.

#### Other (advanced?) calculations
- Modulo divides the number with x. As the result is integer, we can now multiply the result back with the number. Subtracting this number from the original, we get the modulo.
- Modulo Exponentiation is implemented using pseudocode based on Applied Cryptography by Bruce Schneier. It uses multiply and modulo to calculate the result.
- toBitArray checks if the right most number is uneven, if it is, there is a bit. Then it divides by 2 and loops.
- toByteArray checks each byte and multiplies the "bit" number every time by 2. If there is a bit, it adds the number to a result MyBigInteger.

## (My)Random

- Random is implemented as pseudo random number generator (PRNG), also known as deterministic random bit generator (DRBG).
- Random takes seed from system time. Seed can also be given (for testing).
- Random has only one method, nextByteArray(int length), which return a byte array of certain length. The first byte has it's 8th (leftmost) bit 0, as java uses twos complement for integers. This makes it positive, as we don't handle negative integers. 

## (My)Pair

- Pair can be given two objects, a key and a value. Both can be returned with methods. Once pair is created, it can not be changed.

## IO

- IO uses these classes: Scanner, File, FileWriter, FileReader, BufferedReader
- As IO can access hard drive, there are validations which files can be read and saved. User can only access and write to files which are in the `keys` directory.
- `keys` directory is automatically created if the directory does not exist.

## UI

- User Interface was created from scratch, keeping simplicity in mind. There are only few commands to run.
