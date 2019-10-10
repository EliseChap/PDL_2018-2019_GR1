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

1. From the main menu, choose the menu VCS. 
<br><img src= "H:/workspace/PDL/capture1off.png"><br>


In the Clone Repository dialog, specify the URL of the repository that you want to clone. You can select a repository from the list of all GitHub projects associated with your account and the organization that your account belongs to.

In the Directory field, enter the path to the folder where your local Git repository will be created.

Click Clone. If you want to create a project based on these sources, click Yes in the confirmation dialog. IntelliJ IDEA will automatically set Git root mapping to the project root directory.






## Launch the App
Now you can use the app. 




## Running the tests

To start the test, you have to enter:
```
mvn test
```
*Then, it exists different type of test:* <br>

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







