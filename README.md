[![Build Status](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1.svg?branch=master)](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=PDL_2018-2019:Groupe1&metric=alert_status)](https://sonarcloud.io/dashboard?id=PDL_2018-2019%3AGroupe1)

# WikiMatrix


**Goal**

Wikipedia is a universal collaborative digital encyclopedia. A large community consults and feeds Wikipedia regularly, the data on the site are therefore difficult to exploit because of their heterogeneity. Thus, each contributor writes himself the wikicode of his pages, which can lead to big differences between the pages. Moreover, it is not easy at present to extract tables, the existing solutions being rudimentary (Ex: copy / paste) and limited. Wikimatrix will allow the export of a maximum of Wikipedia tabular data to CSV format files. The strength of this project is to solve the heterogeneity of the different tables. Extraction allows a better reuse of data, especially in the fields of statistics and analysis.

**Description**

The user can request the extraction, via a command line, of one or more URLs (instantiation of automatic URLs via a txt file). To interact with the machine, the user has at his disposal a list of commands. It can request to retrieve tables from HTML, Wikicode or both at the same time.

## Getting Started

** Launch the class App **

** Enter one or more URLs. **

The commands to be filled in the terminal.

** Import a .txt file where all the URLs are stored: **

```
-import[C://FileAddress]
```

When using the command -import [C: // FileAddress] this extracts the list of URLs from a file. In order to standardize the input file, it was decided in the first version of our application to be limited to "txt" format. Inside this file, the data will be delimited by the "; '" character. Thus each URL will be separated by this character.

**URL**  

```
-url[Address]
```

**Choose html and / or Wikicode extraction**  

```
-html 
```

Extraction of data by HTML code

```
-wikicode
```

Extraction of data by Wikicode code


**Choose the delimiter in the final CSV**

```
-delimit[,] : 
```

**Register the CSV**

```
-save[c//] :
```

**Register the CSV (s) under which name**

```
-name[fichier.csv] :
```

Attention, the import and url commands can not be realized at the same time. The -html and -wikicode commands can be cumulated to have both extractions simultaneously on the same tables.

**Examples of commands accepted by the application:**

```
WikiMatrix -import[C:\Users\Sophie\Documents\Dossier cours\liens.txt] -html 
```

This command allows the creation of CSV files from URLs that have "links.txt". The extraction will be done from the HTML code. All other parameters will be defined by their default value.

```
WikiMatrix -url[https://fr.wikipedia.org/wiki/Rennes] -html -wikicode -delimit[;] -save[C:\ Users\Sophie\Documents\] 
```
The program extracts the URL and parses the page with both algorithms. Using this command, the user sets the delimiter and the save location.


### Prerequisites


```
IDE (Eclipse or IntelliJ IDEA)
```

## Running the tests

To start the tests enter:

```
mvn test
```

### Break down into end to end tests


Launch the 300 Urls, this is the test that is general in the project.

```
BenchTest
```

Test The Line Command class to check if the command is validated

```
TestCommandLine
```
Test the Html and WikiText comparison

```
TestComparateur
```

Test the creation of CSV

```
TestCsv
```
Test the class Extractor

```
TestExtracteur
```
Test reading urls

```
TestFichier
```

Html extraction test

```
TestHtml
```

Test the creation of Tableau

```
TestTableau
```

Extracting the url to the extractor

```
TestUrl
```

Test the Wikitext class

```
TestWikitext
```





## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Annexe


Please read  [Cahier-des-charges-Rendu.pdf] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES/Cahier-des-charges-Rendu.pdf) for details on our code of conduct, and the process for submitting pull requests to us.

Please read  [ANNEXES] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES) for details on our code of conduct, and the process for submitting pull requests to us.


## Authors

**Margaux Boullé**

**Sophie Bouvry**

**Sullivan Dairou**

**Kénan Lethimonier-Herout**


See also the list of [contributors](https://github.com/SulliDai/PDL_2018-2019_GR1/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

