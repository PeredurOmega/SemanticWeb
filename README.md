# Gesearch - Search Engine for French "Grandes Écoles"

Search Engine for "Grandes Écoles" based on DBpedia (https://www.dbpedia.org/) and implemented with Kotlin/JS and React.

This project has been realized in the as part of the semantic web course (INSA Lyon 4IFA). We made this project over a period of 3 weeks and as a team of 5 people.

The website is available at https://pereduromega.github.io/SemanticWeb.

## The idea

Geasearch is a search engine which aims at finding information about the French "Grandes Écoles".
The application is composed of several pages : 
- Main page : it displays the main search bar of the application.
- Search page : it displays the results found by the search engine. It lists the main information of the universities in the result and shows their location on a map. 
- School page : it displays all the information about a university. It also lists some alma mater and teacher of the university. 
- Person page : it displays all the information about a person. It also displays the educational background of the person thus allowing the user to check the information of the associated schools. 
- City page : it displays all the information about a city. The user can reach this page by clicking on the location of the university in the school page or on the related pin on the map.

## Application navigation graph

You can see below the navigation graph of the application which shows the relations between the different pages.

![Geasearch Navigation Graph](./img/gesearch-navigation-graph.png)

## How does it works ?

The search engine has an autocomplete feature to facilitate your searches. 
It uses the DBpedia Lookup (https://lookup.dbpedia.org/) feature to find ressources which match with the key words in input.
This search engine uses SPARQL queries based on the resources found with the Lookup to search in the DBpedia database and gets information about the universities.

# How to use the search engine ?

You can check the project on the following URL : 

https://pereduromega.github.io/SemanticWeb

Be aware that due to the absence of a load balancer, you can't reload a webpage or access a URL directly (because it's a single app page).

# How to run the project locally ?

## Requirements
<a href="https://www.jetbrains.com/fr-fr/idea/"><img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white"/></a>

<a href="https://kotlinlang.org/"><img src="https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white"/>
&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Version%20-1.6.10-blue.svg"/></a>

<a href="https://gradle.org/"><img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white"/>
&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Version%20-7.3.3-02303A.svg"/></a>

<a href="https://en.reactjs.org/"><img src="https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Version%20-17.0.2-20232A.svg"/></a>

## Setup the project

Firstly, you need to pull the project in an IntelliJ IDEA's new project. <br/> 

Then, you need to make sure your gradle is configured correctly (i.e. with jdk 17).

Finally, you can run the project using the Gradle browserRun : 
```
gradle run
```

If you encounter any issue, feel free to contact us through GitHub or by email.
