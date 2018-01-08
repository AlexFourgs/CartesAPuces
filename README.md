Atelier Cartes à Puces
===
###### Guillaume Renier & Ghiles Mostafaoui

# Présentation
Dépôt pour le projet "Cartes à puces" réalisé à l'Université de Cergy-Pontoise durant le Master 2 IISC.

# Groupe

# Installation

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
```bash
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
