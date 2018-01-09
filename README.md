Atelier Cartes à Puces
===
###### Guillaume Renier & Ghiles Mostafaoui

# Présentation

Dépôt pour le projet "Cartes à puces" réalisé à l'Université de Cergy-Pontoise durant le Master 2 IISC.

# Groupe

* Alexis Aktor
* Alexandre Fourgs
* Roméo Giorgio
* Vincent Monot
* Radnoumane Mossabely
* Antoine Regnier
* Zineb Tazi

# Installations 


## Lecteur de carte

### Linux

Sous Linux (base Debian), il est nécessaire d'avoir certains paquets et drivers installés pour pouvoir utiliser les lecteurs de cartes.

Dans un terminal, lancer la commande suivante :
```bash
sudo apt install pcscd libpcsclite1 pcsc-tools
```

Par la suite, suivant votre lecteur, il vous faudra installer le pilote PC/SC correspondant à votre périphérique. Un certain nombre est déjà fourni dans les dépôts Ubuntu. Pour savoir lequel est nécessaire à l'utilisation de votre carte entrez la commande suivante dans un terminal : 
```bash
apt-cache search "PC/SC driver"
```

Vous obtiendrez une réponse qui vous permettra de savoir quel paquet installer : 
```
libacr38u - PC/SC driver for the ACR38U smart card reader
libasedrive-serial - PC/SC driver for the Athena ASEDrive IIIe serial smart card reader
libasedrive-usb - PC/SC driver for the Athena ASEDrive IIIe USB smart card reader
libccid - PC/SC driver for USB CCID smart card readers
libgcr410 - PC/SC driver for GemPlus GCR410 serial SmartCard interface
libgempc410 - PC/SC driver for the GemPC 410, 412, 413 and 415 smart card readers
libgempc430 - PC/SC driver for the GemPC 430, 432, 435 smart card readers
pcsc-omnikey - PC/SC driver for Omnikey Cardman Smartcard readers (binary-only)
```

Installez le paquet correspondant à votre type de carte. Le paquet libccid installé par défaut avec pcscd est le pilote PC/SC pour le standard CCID. Il permet la reconnaissance d'un grand nombre de lecteurs génériques basés sur ce standard.

Pour vérifier que tout fonctionne, branchez votre lecteur de carte et lancez la commande suivante :
```bash
pcsc_scan
```
Le script devrait renvoyer le résultat suivant :
```
PC/SC device scanner
V 1.4.25 (c) 2001-2011, Ludovic Rousseau <ludovic.rousseau@free.fr>
Compiled with PC/SC lite version: 1.8.14
Using reader plug'n play mechanism
Scanning present readers...
0: Gemalto PC Twin Reader 00 00

Mon Jan  8 13:16:29 2018
Reader 0: Gemalto PC Twin Reader 00 00
  Card state: Card removed, 
```

Par exemple, si l'on branche une carte vitale française de 2010, on obtient le résultat suivant :
```
Mon Jan  8 13:18:40 2018
Reader 0: Gemalto PC Twin Reader 00 00
  Card state: Card inserted, 
  ATR: 3B 75 13 00 00 45 09 EA 90 00

ATR: 3B 75 13 00 00 45 09 EA 90 00
+ TS = 3B --> Direct Convention
+ T0 = 75, Y(1): 0111, K: 5 (historical bytes)
  TA(1) = 13 --> Fi=372, Di=4, 93 cycles/ETU
    43010 bits/s at 4 MHz, fMax for Fi = 5 MHz => 53763 bits/s
  TB(1) = 00 --> VPP is not electrically connected
  TC(1) = 00 --> Extra guard time: 0
+ Historical bytes: 45 09 EA 90 00
  Category indicator byte: 45 (proprietary format)

Possibly identified card (using /home/alex/.cache/smartcard_list.txt):
3B 75 13 00 00 45 09 EA 90 00
        Carte Vitale 2 (French health card)
```

### Windows

Sur windows, les pilotes PC/SC sont installés de base.
