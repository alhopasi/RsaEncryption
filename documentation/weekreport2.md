# Viikko 1

## Mitä viikon aikana on tehty

- Tutustumista RSA-salaukseen.
- RSA-salaus toimivaksi.
- Tekstikäyttöliittymän aloitus

## Ongelmia

- 

## Pohdintaa

- RSA-salausta tutkiessa huomasin, että salattava teksti ei voi olla pidempi kuin salausavain (2048 bittiä).
- Useimmiten käytetään AES-salausta tekstin salaamiseen, ja RSA:lla salataan AES-avain. En kuitenkaan ajatellut lähteä toteuttamaan AES-salausta.
- Salattava teksti usein myös pehmustetaan (padding), että se saadaan aina tietyn mittaiseksi. Tämä vaikeuttaa salauksen purkamista ilman avainta. Rsa-salaus pitäisi aina pehmustaa. Pehmustamisen lisääminen projektiin on myös oma pieni projektinsa.

## Kysymyksiä

- Tarvitseeko tiedoston tallentamiseen ja lukemiseen luoda oma tietorakenne?
- Tarvitseeko lukijan syötteen lukemiseen luoda oma tietorakenne (Scanner)?

## Tunteja käytetty

- RSA-salaukseen tutustumiseen ja ongelmien ratkontaan 5h.
- Avaingeneraattorin viimeistelyyn 1h.
- Tekstin salaamiseen ja purkamiseen 4h.
- Dokumentaatioon 2.5h.
- Yksikkötesteihin 1h.

## Tämän viikon tavoitteiden tarkastelu
- Tavoitteena oli saada tehtyä toimiva RSA-avaingeneraattori. Sellainen on nyt olemassa ja sillä voi salata ja purkaa tekstiä.

## Ensi viikko

- Tavoitteena lisätä pehmustaminen OAEP:llä. OAEP käyttää kahta hash-algoritmiä, joista toiseksi valitsin SHA-256:n, toista en ole vielä valinnut. Jos nämä saisi toimiviksi niin sitten voi aloittaa omien tietorakenteiden rakentamista.
