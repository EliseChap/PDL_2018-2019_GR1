Comment on construit le projet à partir du code source?
Comment on exécute les suites de tests?
Comment on exécute le logiciel?

# INSTALL 

## Get the project 

From GitHub, you must follow this link: https://github.com/EliseChap/PDL_2018-2019_GR1

**For Eclipse**

*Setting* <br>
First, you have to add the git repository to Eclipse. It's important to have the view visible on your Eclipse. For that, from the menu 'Window > Show views > Other > Git > Git Repositories'.
If you have github desktop you can clone the project, in the view select "Add an existing local Git Repository" and choose your project.
<br><img src= "ANNEXES/ExistingLocalGit.png"><br>
<br><img src= "ANNEXES/projectClone.png"><br>

If you doesn't use the github desktop you can download the projet, in the wiew select "Clone a Git Repository" and choose your project.<br><img src= "ANNEXES/imgGitRepositories.png"><br>
<br><img srx= "ANNEXES/projectDownload.png"><br>

**For IntelliJ**







## Launch the App
Now you can use the app. 




## Running the tests

To start the test, you have to enter:
```
mvn test
```
Then, it exists different type of test: <br>

Launch the 300 Urls, general test
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







