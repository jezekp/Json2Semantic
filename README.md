# Json2Semantic
The project is a layer on the top of [Semantic-Framework](https://github.com/NEUROINFORMATICS-GROUP-FAV-KIV-ZCU/Semantic-Framework) allowing reading of json documents and transforming them to RDF/OWL output

Json given as a parameter is transferred to an ontology RDF/OWL document.


## Prerequisities
* JDK installed
* Maven installed
## Run
* clone repository
* mvn package
* java -jar target/Json2Semantic-1.0-SNAPSHOT-jar-with-dependencies.jar filename


### Notes

* Transformation is driven by template file(s). Template files represent a structure that all transformed files must follow
* Template files are stored in the src/main/resources/schema/json folder.
* mvn package is needed for the first run and in every run when any template file is changed.
* Filename param is a json file respecting the structure given by a template file.
* An output RDF document is printed to the standard output.

