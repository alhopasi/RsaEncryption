# Week 4

## What's been done during the week

- Own code for Prime generation, previously used a ready library.
- Own code for Prime checking, previously used a ready library.
- Own datastructure for Pair
- Own datastructure for Random.

## Problems

- Making own powMod algorithm work really fast is a problem. Checking one key takes around 8 ms, while the algorithm in BigInteger class works in under 1 ms.
- Running tests takes a loooong time because of this.
- Couldn't get padding to work.

## Notes

- Without working padding the encrypted text is vulnerable to chosen plaintext attack from users with public key. Encrypting a key will always result into same encrypted message. With padding the message would be random, but would still be able to be decrypted.

## Questions

- How should I test my project? What kind of tests can and should be done?

## Hours used

- Prime generation code 1h
- Refactoring 1h
- Prime checking code (modPow mostly) 8h
- Pair 5 min
- Random 2h
- Tests 1h
- Documentation 30 min

## Did I meet this weeks goals

- Prime generation now works with own code. Yes on this part.
- Pair is working. Yes.
- Random is working. Yes.

## Next week

- Goal is to start working on BigInteger implementation.
- And try to do some testing maybe on... what?
