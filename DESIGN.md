# DESIGN

## Static models
 ### Class diagram
<img src = "/ANNEXES/diagrammes/ClassDiagram.png"> <br>

## Main classes :
* **Fichier** <br/>
Recover the .txt document with the urls to use and convert it into an arraylist.
* **Extracteur** <br/>
Implements an interface.
* **Url** <br/>
Implements an interface. Check the url written in the .txt document to know if it's a wikipedia url and then create the extractor asked.
* **Tableau** <br/>
Implements an interface. Tables extracted from wikipedia.
* **Csv** <br/>
Implements an interface. Create the csv document recovered from tables extracted.
* **Comparateur** <br/>
Implements an interface. Compare the wikicode's table and the html one.
* **Html** <br/>
Its a type of extractor. The HTML format is present in each web pages.
* **Wikitext** <br/>
Its a type of extractor. The wikicode format is present in each wikipedia pages.
* **CommandLine** <br/>
Implements an interface. Verify if the command line typed is written in the good syntax.

## Dynamic models
  ### Use case diagram
  <img src = "/ANNEXES/diagrammes/Use_Case_Diagram.PNG"> <br>
  ### Use case scenarios <br>
  * 1 - Configure the output directory <br> <br>
    **Actors :** The customer <br>
    **Description :** The application provides a backup path for the default file. The user has the option to change this path by adding a command when importing the URL. <br>
    **Precondition :** The import command for the file or URL must be valid. <br>
    **Start :** The customer launches the application. <br> <br>
  Description <br> <br>
    **Optimal scenario** <br> <br>
        1) The system is waiting fro an instruction from the user. <br>
        2) The user type a command line starting with the import of a URL or .txt file containing a multitude of URLs. Then he enters the command to configure the path of
          save the file. <br>
        3) Checking the validity of the command line. <br>
        4) The system checks the accessibility of the indicated path. <br>
        5) The system processes. <br>
        6) The system saves the created CSV file to the address provided by the user followed by the name of the language used by the extraction of data. <br> <br>
    **Exceptional scenarios** <br> <br>
        3a) One of the commands in the command line is invalid. The system displays
           an error message and waiting for a new statement. <br>
        4a) If the path is not accessible because the user does not have rights for that location or because the path does not exist, the system displays an error message to the user and waits for a new instruction. <br> <br>
    **Alternative scenarios** <br> <br>
        2a) The user does not inform the command allowing the configuration of the path of save the file. In this case the backup path will be the default path of the application. <br> <br>
    **Fin :** Exceptional scenarios 3a) and 4a) <br>
    **Post condition :** The generated CSV file is saved to the location specified by the user. <br> <br>
  * 2 - Generate CSV from HTML and wikicode <br> <br>
    **Actors :** The customer <br>
    **Description :** The client has the ability to generate two CSV files, one from the HTML code
                      a Wikipedia page, the other then the HTML from the same page. <br>
    **Precondition :** / <br>
    **Start :** The customer launches the application. <br> <br>
  Description <br> <br>
    **Optimal scenario** <br> <br>
        1) The system is waiting fro an instruction from the user. <br>
        2) The client fills a command line importing a .txt file containing a multitude URL or a simple URL. The order must also indicate the type of importation of data thanks to specific commands. The customer can refer to the user chart. <br>
        3) Checking the validity of the command line. <br>
        4) The system retrieves the HTML code of the page. <br>
        5) The system checks for the presence of tables in the page. <br>
        6) For each table present, the system checks their eligibility for treatment. It must exclude
           too complex tables of the process. <br>
        7) The system processes the tables one by one to extract the data. <br>
        8) The system creates a CSV file, per table, and generates it in a default directory. The name of the file defaults to the name of the array followed by the language used when extracting the data. <br>
        9) The system restarts step 4 with the Wikicode. <br> <br>
    **Exceptional scenarios** <br> <br>
        2a) The URL is invalid or the command does not exist. The system informs the user with a
            error message and waiting for new instructions. <br> <br>
    **Alternative scenarios** <br> <br>
        2a) No table is present in the page entered through the URL. The system informs the user of the absence of tables in the case of a single URL with an error message and is waiting for an instruction. In the case of a file with a multitude of URLs the system continues its processing with the following URL and will display an informational message at the end of processing. <br>
        6a) A table that is too complex to convert to CSV should not be processed, in which case the system stores an information message that it will deliver to the user at the end of any processing. <br>
        7a) For complex data like cell mergers, the line is deleted. The system stores an information message to the user, it will restore it to the end of any treatment. <br> <br>
    **Fin :** Exceptional scenarios 3a) and alternative scenarios 2a) <br>
    **Post condition :** 1 CSV file generated from HTML, 1 file generated from the wikicode for each painting <br> <br>
  * 3 - Configure URL entry mode <br> <br>
    **Actors :** The customer <br>
    **Description :** The customer has the ability to configure the URL input mode. Wikipedia URLs
                      can be imported through a file or entered one by one through the command lines.<br>
    **Precondition :** / <br>
    **Start :** The customer launches the application. <br> <br>
  Description <br> <br>
    **Optimal scenario** <br> <br>
        1) The system is waiting for a directive from the user. <br>
        2) The user enters the command to enter a URL address or a file containing amultitude of addresses. The command must start with the import of a file or URL. <br>
        3) The system interprets the command and checks the URLs. <br> <br>
    **Exceptional scenarios** <br> <br>
        2a) The filled command does not exist, in this case a help message is displayed on thescreen.<br>
        3a) If one or more of the addresses is not a wikipedia address, the system informs the userthat the addresses are wrong with an error message, he waits for a new instruction from the user. <br> <br>
    **Alternative scenarios** <br> <br>   
    **Fin :** Exceptional scenarios 2a) and 3a) <br>
    **Post condition :** The system takes into account the import of either a file or a single URL. <br> <br>
  * 4 - Configure the output CSV file name <br> <br>
    **Actors :** The customer <br>
    **Description :** The application proposes a default name for the output file (s). The user has nevertheless the possibility of changing this name by adding a command when importing the URL. <br>
    **Precondition :** The user must specify a file name that is not already used in thecurrent directory. <br>
    **Start :** The customer launches the application. <br> <br>
  Description <br> <br>
    **Optimal scenario** <br> <br>
        1) The system is waiting fro an instruction from the user. <br>
        2) The user types a command line starting with the import of a URL or .txt file containing one or more URLs followed by the command to configure a file name custom to save the generated CSV file as output. <br>
        3) The system checks the integrity of the command line. <br>
        4) The system checks that no file of the same name already exists in the current directory.<br>
        5) The system processes the extraction of the tables at the specified URL. <br>
        6) The system saves the CSV file under the name provided by the user. <br> <br>
    **Exceptional scenarios** <br> <br>
        3a) One of the commands in the user's statement is invalid. The system displays an error message and waits for a new statement, always on the command line. <br>
        4a) If the name chosen by the user is already used by another file in the current directory, the system displays a message to the user and increments a valid name. <br> <br>
    **Alternative scenarios** <br> <br>
        2a) The user does not supply the configuration command for the output file name. In this case, the file name will follow the default configuration of the application, and will have the name of the file, the name of the table concerned. <br> <br>
    **Fin :** <br>
    **Post condition :** The generated CSV file is saved under the name specified by the user. <br> <br>
  * 5 - Configure the CSV separator output <br> <br>
    **Actors :** The customer <br>
    **Description :** The customer has the possibility to configure the separator that will be used by the system for outputting the CSV file, adding a command when importing URL. <br>
    **Precondition :** The user will have to indicate a separator included in the list of separators
                       authorized by the system. <br>
    **Start :** The customer launches the application. <br> <br>
  Description <br> <br>
    **Optimal scenario** <br> <br>
        1) The system is waiting fro an instruction from the user. <br>
        2) The user types a command line starting with the import of a URL or .txt file containing one or more URLs followed by the command to configure a file name custom to save the generated CSV file as output. <br>
        3) The system checks the integrity of the command line. <br>
        4) The system checks that the separator chosen by the user is a separator authorized by the
           system. <br>
        5) The system processes the extraction of the tables at the specified URL. <br>
        6) The system generates and saves the CSV file using the user-specified separator. <br> <br>
    **Exceptional scenarios** <br> <br>
        3a) One of the commands in the user's statement is invalid. The system displays an error message and waits for a new statement, always on the command line. <br>
        4a) If the separator chosen by the user is prohibited by the system (for the sake of consistency of the generated CSV file), the system displays a message to the user and the system selects a valid separator. If in the data, there is the separator, the user will               be informed by an error message for restart the command concerned. <br>
        4b) If it is the system that chooses the separator and there is data that uses the system in
            choose another. <br> <br>
    **Alternative scenarios** <br> <br>
        2a) The user does not enter the separator configuration command for generation CSV output. In this case, the separator used by the system will take its default value, that is to say "; ". <br> <br>
    **Fin :** <br>
    **Post condition :** The different elements of the output CSV file are separated using the
                         separator indicated by the user. <br> <br>  
## Diagram
 ### State diagram
 <img src = "/ANNEXES/diagrammes/State_Diagram.PNG"> <br>
