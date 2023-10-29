# Summarizr
A Spring Boot 3 microservice that gets summarized text from the ChatGPT API.
The application is fully non-blocking using Netty/WebFlux, with WebClient for server-to-server calls. 

## Building the application
Build the application with the following command
        
    mvn clean install

The env variable OPENAI_API_KEY needs to be set in Intellij or in the terminal before running the application.
This requires acquiring your key from OpenAI's developer portal.

The applicaton can be started locally either through Intellij, or with the following command:

    mvn spring-boot:run

***

## Spring Native Support
In order to build the native image, the following command can be used:

    mvn -Pnative spring-boot:build-image

The image can then be launched with Docker using the following command:
    
    docker run -p 8080:8080 -e OPENAI_API_KEY=$OPEN_API_KEY docker.io/library/summarizr:0.0.1-SNAPSHOT
