[![Build Status](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1.svg?branch=master)](https://travis-ci.org/SulliDai/PDL_2018-2019_GR1)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=PDL_2018-2019:Groupe1&metric=alert_status)](https://sonarcloud.io/dashboard?id=PDL_2018-2019%3AGroupe1)

# WikiMatrix


**Goal**

Wikipedia is a universal collaborative digital encyclopedia. A large community consults and feeds Wikipedia regularly, the data on the site are therefore difficult to exploit because of their heterogeneity. Thus, each contributor writes himself the wikicode of his pages, which can lead to big differences between the pages. Moreover, it is not easy at present to extract tables, the existing solutions being rudimentary (Ex: copy / paste) and limited. Wikimatrix will allow the export of a maximum of Wikipedia tabular data to CSV format files. The strength of this project is to solve the heterogeneity of the different tables. Extraction allows a better reuse of data, especially in the fields of statistics and analysis.

**Features**

The user can request the extraction, via a command line, of one or more URLs (instantiation of automatic URLs via a txt file). To interact with the machine, the user has at his disposal a list of commands. It can request to retrieve tables from HTML, Wikicode or both at the same time. Every table has is own CSV file.

The user can also choose the delimiter in the final CSV, the name of the CSV file and where the CSV file is registered. 

Every features is explained more in details in the file <a href="Install.md> Install.md </a>. 

**Future Features**

The extractor doesn't manage to convert complex tables with the wikicode extraction method. In the future, the extractor will have to be able to convert every table in a CSV file with the wikicode extraction method, no matter how complex it is.

### System requirements

```
IDE (Eclipse or IntelliJ IDEA)
```
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Annexe

Please read  [Cahier-des-charges-Rendu.pdf] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES/Cahier-des-charges-Rendu.pdf) for details on our code of conduct, and the process for submitting pull requests to us.

Please read  [ANNEXES] (https://github.com/SulliDai/PDL_2018-2019_GR1/blob/master/ANNEXES) for details on our code of conduct, and the process for submitting pull requests to us.


## Authors

**2019-2020**

Elise Chapon 

Alexis Diabat 

Célie Rault 

Maëlla Gheraia 

**2018-2019**

Margaux Boullé

Sophie Bouvry

Sullivan Dairou

Kénan Lethimonier-Herout


See also the list of [contributors](https://github.com/SulliDai/PDL_2018-2019_GR1/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details









