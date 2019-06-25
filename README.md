# Text Search Engine

## Table of contents
* [General info](#general-info)
* [Getting Started](#getting-started)
* [Prerequisites](#prerequisites)
* [Build the project](#build-the-project)
* [Application execution](#application-execution)
* [Running the tests](#running-the-tests)
* [Test purpose](#test-purpose)
* [Built With](#built-with)
* [Authors](#authors)
* [Acknowledgments](#acknowledgments)

## General info

Text Search Engine that allows to search words contained by the files located under a specified path. The application reads all the text files in the given directory, building an in memory representation of the files and their contents, and then give a command prompt at which interactive searches can be performed. 
 
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This project requires the following prerequisites:

```
- Java 8+: Install [Java JDK](https://openjdk.java.net/install/) version 8 or higher.
- Maven: Install [Apache Maven](https://maven.apache.org/download.cgi) version 3 or higher
- Git: Install [git](https://git-scm.com/downloads)
```

### Build the project

In order to build the project follow these steps:

```
- Clone this repository: git clone https://github.com/Siegolas/text-search-engine.git
- Go into the repository root folder
- Build the project: mvn package
```

## Application execution

Once the project has been build, it can be executed with this command:

```
- java -jar target/text-search-engine-1.0.0.jar {directory path}
```

It also can be executed by Maven with this command:

```
- mvn exec:java -Dexec.args="{directory path}"
```

As an example, execute this command:

```
mvn exec:java -Dexec.args="src/main/resources/files" 
```

The file processor will iterate through the files contained under the path 'src/main/resources/files' and will obtain an in memory representation of the words appearances in the files. Then it shows the text 'search>' indicating that the user can introduce words to search in the 'in-memory' structure.

Example of use, write 'first file' and press ENTER:

```
search>first file
```

Then, the search processor performs the search and presents the results,
showing the percentage of match for the files that received matches in the search:

```
src/main/resources/files/filename1 : 100.0%
src/main/resources/files/filename3 : 50.0%
src/main/resources/files/filename2 : 50.0%
```

The next fragment shows a search with no match at all:

```
search>Cthulhu
no matches found
```

## Running the tests

Each method contained in the Services of this project has a Junit test. In order to execute the tests execute the following command:

```
- Execute the tests: mvn test
```

### Test purpose

For the development of this project, the tests have been taken into account from the beginning. Therefore, every small functionality within the file processing algorithm or the search algorithm has been programmed as an independent method. This has made it possible to test each of the small functionalities in an isolated way, being able to test it in detail.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Sergio Caballero** - *Initial development* - [TextSearchEngine](https://github.com/Siegolas/text-search-engine)

## Acknowledgments

* Java Lambda Expressions
* JUnit
* Inspiration