# Week 6

## What's been done during the week

- Working on implementing BigInteger.

## Problems

- My own datastructure for BigInteger is not fast enough.
- Couldn't get padding to work.

## Notes

- I tried creating Big Integer by using only Strings. Now I'm giving up on that approach. I finished all datastructures, but they are way too slow. I believe the correct route would be to work on byte/int arrays that contain bytes. However, I'm not sure how to implement that.
- I used so many hours on trying to implement Big Integer, that I haven't had time for refactoring the code.
- Without working padding the encrypted text is vulnerable to chosen plaintext attack from users with public key. Encrypting a text will always result into same encrypted message. With padding the message would be random, but would still be able to be decrypted.

## Questions

- None.

## Hours used

- BigInteger 17h
- Documentation 1h
- Tests 1h 30 min

## Did I meet this weeks goals

- Worked on BigInteger, so yes... but, as it's not working, there is no real progress made on the project.
- No testing done yet on performance, gotta finish BigInteger first.

## Next week

- Goal is to try a new approach for BigInteger implementation.
- Finish documentation.
- Do performance testing on MyBigInteger vs. BigInteger.
- Do performance testing on RSA key generation, decryption and encryption vs. imported classes. (with BigInteger and with MyBigInteger)
