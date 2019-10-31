# marsrover-server

Simple Java 8 Spring boot REST API to download and serve Mars Rover photos using the NASA api.

## REST API endpoints

* `GET /api/v1/photo?[earthDate=ACCEPTED_DATE_FORMAT][&rover=ROVER_NAME]` - retrives and caches photos. If date is not given, uses seeded dates. If rover is not given defaults to Curiosity
* `GET /api/v1/photo/{PHOTO_ID}?imgSrc=URI` - returns the photo image
* `POST /api/v1/photo/seed` - reloads photos from seed date list
* `DELETE /api/v1/photo` - deletes cache

## Configuration
The following are configurable from `resources/application.yml`:
* `nasa.client.apikey` - API key
* `mars.cache.dir` - image download cache directory
* `nasa.client.dateFormat` - NASA request earth date format
* `mars.photo.seedFile` - seed list date file. NOTE: April 31, 2018 is an invalid date, this one will be skipped and an error logged

## prerequisites
* Java 8 SDK
* JAVA_HOME set

## build
* Clone or download this repository
* Enter marsrover-server directory and build

`./gradlew clean build bootJar`

This will download all dependencies needed, run the tests, and generate a jar file.

## run from jar
After building, a jar file named `marsrover-server-0.0.1-SNAPSHOT.jar` will be located in the `build/libs` directory. 
To run the jar from the root of the project, use the following command:

`java -jar build/libs/marsrover-server-0.0.1-SNAPSHOT.jar`
Spring Boot will start up the server at port 8080 and APIs will be accessible at http://localhost:8080
