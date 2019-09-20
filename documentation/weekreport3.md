# Week 3

## What's been done during the week

- Familiarizing with OAEP-padding.
- Trying to implement OAEP, failed horribly.
- Refactoring code to Checkstyle standards.
- Improving UI towards project goal. Save / Load system for keys. Validating inputs.

## Problems

- Didn't get RSA-256 to work. Scratching that for now, as I focus on creating own data structures for RSA.

## Notes

- Without working padding the encrypted text is vulnerable to chosen plaintext attack from users with public key.

## Questions

- none

## Hours used

- OAEP-padding and SHA-256 6h
- Refactoring code 4h
- Adding save / load to TextUi 2h
- Polishing TextUi 2h
- More tests to new refactored code 3h

## Did I fill this weeks goals

- Padding failed. So, no. But I made the program completed. Primes are however generated from a ready library, so I need to change them to my own generation.

## Next week

- Goal is to finish creating the primes with big integer and start working on my own datastructure for Pair and BigInteger and random.
