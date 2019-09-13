# Määrittelydokumentti

Projektin tavoitteena on luoda toimiva RSA-avainpari generaattori, sekä mahdollisuus käyttää RSA-avaimia salauksen luomiseen ja purkamiseen lyhyelle tekstille.

## Algoritmit ja tietorakenteet

- Toteutetaan luku, joka käyttää byte-taulukkoa, ja voi olla minkä mittainen tahansa.
- Luvulle mahdollisuus monenlaisiin operaatioihin.
- Toteutetaan satunnaisgeneraattori, jolla voi arpoa byte-taulukon (luvun).
- Toteutetaan pari, jolle voi antaa olioita.

## Ongelma

- Uusien RSA-avainten luominen: Yksityinen sekä julkinen.
- Avaimilla tekstin salaaminen ja avaaminen.
- Avainten tallentaminen ja lukeminen tiedostosta.
- Tekstin salauksessa pehmustaminen (padding).
- Tekstin pehmustamisessa Hashaus. (SHA-256 ja joku muu?)
- Tekstin salauksessa allekirjoituksen tekeminen.

## Ohjelman syötteet.

- Valitaan, halutaanko luoda rsa-avainpari, vai käyttää jo luotua.
- Salataanko vai puretaanko?
- Mikä on avaimen nimi?
- Teksti mikä salataan / puretaan.

## Aika- ja tilavaativuudet

- RSA-salausta käytettäessä salattavan tekstin tulee olla lyhyempi kuin avaimen pituus. Teksti muutetaan (yleensä) yhtä pitkäksi kuin avain, lisäämällä siihen pehmustetta (padding). Salaukseen kuluva aika siis riippuu avaimen pituudesta. Jos avaimesta halutaan hyvin turvallinen, tulee eksponentin sekä avaimen pituuden olla riittävän isot. Tällöin myös salaamiseen käytetty aika kasvaa.
- Syötteellä ei siis itsessään ole oikeastaan väliä, koska se muutetaan aina yhtä pitkäksi kuin avain.
- Aika- ja tilavaativuudet ovat O(n).

## Lähteet

- https://simple.wikipedia.org/wiki/RSA_algorithm
- https://fi.wikipedia.org/wiki/Modulaariaritmetiikan_käänteisluku
- https://security.stackexchange.com/questions/112029/should-sha-1-be-used-with-rsa-oaep
- https://en.wikipedia.org/wiki/SHA-2
- https://en.wikipedia.org/wiki/Exponentiation#Efficient_computation_with_integer_exponents
