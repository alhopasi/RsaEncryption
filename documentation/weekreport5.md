# Week 5

## What's been done during the week

- Made Prime checking way faster by implementing another check.
- Familiarizing with BigInteger.
- Started working on MyBigInteger class and it's methods.

## Problems

- Figuring out how to do byte array conversion to int arrays is not working yet. And many others :D But I'm taking small steps and trying to get there.
- Couldn't get padding to work.

## Notes

- Last week I had problem about Prime checking taking long time, I implemented another check, that checks if the possible prime is divisiable with low number primes. This way I can filter most cases before going to the real prime check, making the process much faster. 
- Without working padding the encrypted text is vulnerable to chosen plaintext attack from users with public key. Encrypting a text will always result into same encrypted message. With padding the message would be random, but would still be able to be decrypted.

## Questions

- Do I need to comment all methods or just public ones?

## Hours used

- Prime checking code 0.5h
- Familiarizing with BigInteger 2h.
- BigInteger and methods implementation 9h.
- Documentation 30 min
- Tests for BigInteger 3h.

## Did I meet this weeks goals

- Been working on BigInteger, so yes.
- No testing done yet on performance, gotta finish BigInteger first.

## Next week

- Goal is to continue BigInteger implementation.
- If I can finish BigInteger, do some performance testing on Java's BigInteger versus mine.
