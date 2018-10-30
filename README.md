# PDL_2018-2019_GR1

→  Objectif général à atteindre

Wikipédia est une encyclopédie numérique collaborative universelle. Une communauté importante consulte et alimente Wikipédia régulièrement, les données présentes dans le site sont donc difficilement exploitables du fait de leur hétérogénéité. Ainsi, chaque contributeur écrit lui-même le wikicode de ses pages, ce qui peut entraîner de grandes divergences entre les pages. De plus, il n’est pas facile actuellement d’extraire des tableaux, les solutions existantes étant rudimentaires (Ex : copier/coller) et limitées.
Wikimatrix permettra l’exportation d’un maximum de données tabulaires de Wikipédia vers des fichiers de format CSV. La force de ce projet est de solutionner l’hétérogénéité des différents tableaux. L’extraction permettra une meilleure réutilisation des données, notamment dans les domaines de la statistique et de l’analyse. Wikipédia étant une plateforme collaborative, le projet sera Open Source, afin de rester dans un esprit de partage. 

→  Description du résultat attendu

A terme, l’utilisateur pourra demander l’extraction, par l’intermédiaire d’une ligne de commande, d’une ou plusieurs URLs (instanciation des URLs automatique via un fichier txt). Pour interagir avec la machine, l’utilisateur aura à sa disposition une liste de commandes. Il existe une charte utilisateur (voir annexe 1) auquel il est possible de se référer. Il pourra demander à récupérer les tableaux à partir du format HTML, du Wikicode ou des deux en même temps. 
Deux cas se présentent alors :

       → L’URL ne peut être traitée, pour plusieurs raisons : 
       
- Le domaine Wikipédia ne peut être pris en compte, nous ne considérons (pour une première version) que les adresses françaises et anglaises Wikipédia, en écartant par exemple les versions régionales. - L’URL ne contient pas de tableaux ou elle ne contient pas de types de tableaux pris en charge par Wikimatrix (voir annexe 2). Dans le cas où une URL présente plusieurs tableaux, l’application traitera chaque tableau indépendamment.
L’utilisateur sera informé de la non prise en compte de sa demande par un message.

      →  L’URL peut être traitée :
      
- L’utilisateur obtiendra en sortie un fichier CSV pour chaque tableau présent dans l’URL. Si il sélectionne les deux formats, il obtiendra un CSV pour chaque tableau extrait. Des messages pourront être affichés à l’utilisateur au cours de l’extraction ( Ex : Cellules fusionnées supprimées …).

L’application se devra d’être :      

-      Fonctionnelle : Traiter un maximum de données tabulaires           
-      Facile d’utilisation : Commandes explicites           
-      Fiable : La fiabilité sera notamment testée avec notre comparateur      
-      Performante : Rapidité d’exécution      
-      Maintenable : Modularité du code, notamment pour une future extension.      
-      Portable : Adaptable à différents OS (Windows, Linux, MacOS...)       
-      Open source : Libre d’accès, documentée.

Les tableaux Wikipédia devront être extraits à partir de deux formats : 

- HTML : Chaque page web est constituée de ce langage à balises. 
- Wikicode : Présent dans chaque page Wikipédia.

L’intérêt pour le client est qu’il peut générer le CSV le plus adapté à son étude. Les deux langages étant différents, ils ne contiennent pas forcément les mêmes informations.

Dans l’objectif de répondre au mieux aux besoins de l’utilisateur, plusieurs options lui seront proposées : 

- Choix du nom du fichier 
- Choix de l’importation : une liste d’URL ou une simple URL 
- A partir de quel format l’extraction est-elle effectuée : html, Wikicode ou les deux ? 
- Le choix de l’emplacement du fichier 
- La délimitation du CSV Chaque option possédera une valeur par défaut tel que la délimitation du CSV qui sera par défaut «,».


