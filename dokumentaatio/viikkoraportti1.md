# Viikko 1

## Mitä viikon aikana on tehty

- Projektin luonti, githubin luonti
- Määrittelydokumentin aloitus
- Projektin suunnittelu

## Ongelmia

- Alkuluvun luominen itse (BigIntegereitä käyttäen) osoittautui hankalaksi. Tai ongelma on saada se juuri tietyn bittimäärän pituiseksi. Ongelmia tuo Java, joka ymmärtääkseni käsittelee kaikki numerot kahden komplementtisina, jolloin se käyttää bittejä kahden komplementin esittämiseen. Näin ollen lopullinen luku on bittimäärältään pienempi.
- RSA:n salauksen ymmärtäminen on haastavaa. Tähän täytyy käyttää enemmän aikaa. 

## Kysymyksiä

- Tarvitseeko BigInteger luoda kokonaan itse nollasta.
- Entä Random?
- SecureRandom, jolla arvotaan esim. satunnainen määrä bittejä?
- Nämä tulevat olemaan haastavia :)

## Tunteja käytetty

- Projektin luontiin ja dokumentaatioon noin 1,5h.
- Aiheeseen perehtymiseen ja koodaukseen n. 5h.

## Ensi viikko

- Tavoitteena saada tehtyä toimiva RSA-avaingeneraattori sekä mahdollisuus salata tekstiä avaimella ja purkaa sitä.
