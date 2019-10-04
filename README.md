# RSA-encryption

Program, which can create RSA-keys and use those keys to encrypt and decrypt text.
Project is a part of course "Aineopintojen harjoitustyö: Tietorakenteet ja algroritmit"

## Documentation

[Project definition](documentation/definition.md)

[Implementation documentation](documentation/implementation.md)

[Testing documentation](documentation/testing.md)

[User Guide](documentation/userguide.md)

## Weekly reports

[Week 1](documentation/weekreport1.md)

[Week 2](documentation/weekreport2.md)

[Week 3](documentation/weekreport3.md)

[Week 4](documentation/weekreport4.md)

[Week 5](documentation/weekreport5.md)

## Compiled program

*(coming later)*

## How to run on command line

- mvn install  (do this first)

- Test: mvn test
- Checkstyle: mvn jxr:jxr checkstyle:checkstyle
- Jacoco: mvn test:jacoco

- Compile: mvn package
- Run: java -jar target/RsaEncryption-1.0-SNAPSHOT.jar
