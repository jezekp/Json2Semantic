# Json2Semantic
The project is a layer on the top of Semantic-Framework. It enables reading of json documents

Document called crcnsjson.json in the src/main/resources/schema/json folder is transferred to an ontologyDocument.rdf.xml file.


## Prerequisities
* JDK installed
* Maven installed
## Run
* clone repository
* mvn package
* java -jar target/SemanticFrameworkTest-1.0-SNAPSHOT-jar-with-dependencies.jar 

### TODO 
* make the input/output parametrizable

### Notes
A org.jsonschema2pojo transformation is done according to the input json file.


