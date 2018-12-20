[![Build Status](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1.svg?branch=master)](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=PDL_2018-2019:Groupe1&metric=alert_status)](https://sonarcloud.io/dashboard?id=PDL_2018-2019%3AGroupe1)

# WikiMatrix


→ Objectif

Wikipédia est une encyclopédie numérique collaborative universelle. Une communauté importante consulte et alimente Wikipédia régulièrement, les données présentes dans le site sont donc difficilement exploitables du fait de leur hétérogénéité. Ainsi, chaque contributeur écrit lui-même le wikicode de ses pages, ce qui peut entraîner de grandes divergences entre les pages. De plus, il n’est pas facile actuellement d’extraire des tableaux, les solutions existantes étant rudimentaires (Ex : copier/coller) et limitées. Wikimatrix permettra l’exportation d’un maximum de données tabulaires de Wikipédia vers des fichiers de format CSV. La force de ce projet est de solutionner l’hétérogénéité des différents tableaux. L’extraction permet une meilleure réutilisation des données, notamment dans les domaines de la statistique et de l’analyse.

→ Description 

L'utilisateur peut demander l’extraction, par l’intermédiaire d’une ligne de commande, d’une ou plusieurs URLs (instanciation des URLs automatique via un fichier txt). Pour interagir avec la machine, l’utilisateur  à sa disposition une liste de commandes. Il peut demander à récupérer les tableaux à partir du format HTML, du Wikicode ou des deux en même temps. 

## Getting Started

- Lancer la classe App

→  Renseigner une ou plusieurs URLs.
Les commandes à renseigner dans le terminal sont en gras.
→ Importer un fichier .txt où sont rangées l’ensemble des URLs   :  
-import[C://AdresseFichier] :
Lorsqu’on utilise la commande -import[C://AdresseFichier] cela extrait la liste des URLs d’un fichier. Afin de normaliser le fichier d’entrée, il a été décidé dans la première version de notre application de se limiter au format “txt”. À l'intérieur de ce fichier, les données seront délimitées par le caractère “ ;’”. Ainsi chaque URL sera séparée par ce caractère.
-url[adresse] : 
→  Choisir extraction html et/ou Wikicode
-html : Extraction des données par le code HTML -wikicode : Extraction des données par le code Wikicode
→ Choisir le délimiteur dans le CSV final
-delimit[,] : 
→ Enregistrer le/les CSV
-save[c//] :
→ Enregistrer le/les CSV sous quel nom
-name[fichier.csv] :
Attention, les commandes import et url ne peuvent pas être réalisées en même temps. Les commandes -html et -wikicode peuvent être cumulées pour avoir les deux extractions simultanément sur les mêmes tableaux.
Page 17
Exemples de commandes acceptées par l’application :
WikiMatrix -import[C:\Users\Sophie\Documents\Dossier cours\liens.txt] -html Cette commande permet la création de fichiers CSV à partir des URL présentent de “liens.txt”. L’extraction sera fera à partir du code HTML. Tous les autres paramètres seront définis par leur valeur par défaut.
WikiMatrix -url[https://fr.wikipedia.org/wiki/Rennes] -html -wikicode -delimit[;] -save[C:\ Users\Sophie\Documents\] Le programme extrait l’URL et analyse la page avec les deux algorithmes. A l’aide de cette commande, l’utilisateur paramètre le délimiteur et l’emplacement de sauvegarde.


### Prerequisites


```
IDE (Eclipse or IntelliJ IDEA)
```

## Running the tests

Pour lancer les tests saisir :

```
mvn test
```

### Break down into end to end tests


Lancer les 300 Urls, c'est le test qui est generale dans le projet.

```
BenchTest
```

Test La classe Commande Line afin de verifie si la commande est valide

```
TestCommandLine
```
Test la comparaison Html et WikiText

```
TestComparateur
```

Test la création des CSV

```
TestCsv
```
Test la classe Extracteur

```
TestExtracteur
```
Test la lecture des urls

```
TestFichier
```

Test l'extraction Html

```
TestHtml
```

Test la création de Tableau

```
TestTableau
```

Extraction de l'url vers l'extracteur

```
TestUrl
```

Test la classe Wikitext

```
TestWikitext
```





## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Annexe


Please read  [Cahier-des-charges-Rendu.pdf] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES/Cahier-des-charges-Rendu.pdf) for details on our code of conduct, and the process for submitting pull requests to us.

Please read  [ANNEXES] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES) for details on our code of conduct, and the process for submitting pull requests to us.


## Authors

* **
→ Margaux Boullé
→ Sophie Bouvry
→ Sullivan Dairou
→ Kénan Lethimonier-Herout 
**

See also the list of [contributors](https://github.com/SulliDai/PDL_2018-2019_GR1/graphs/contributors) who participated in this project.
Cahier-des-charges-Rendu.pdf
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

