# Summarizr
A Spring Boot 3 microservice that gets summarized text from the ChatGPT API.
The application is fully non-blocking using Netty/WebFlux, with WebClient for server-to-server calls. 

## Building the application
Build the application with the following command
        
    mvn clean install

The env variables `OPENAI_API_KEY`, `APP_USER_NAME` and `APP_USER_PASSWORD` need to be set in Intellij or in the terminal 
before running the application.
This requires acquiring your key from OpenAI's developer portal.

The application can be started locally either through Intellij, or with the following command:

    mvn spring-boot:run

***

## Security
The application uses basic username/password security.
This means the username and password have to be added in the request header in base64-encoded form, similar to below:

    Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==

Security can be disabled by setting the variable `SECURITY_ENABLED = false`. This is not advisable for actual usage, but can be helpful fo ra demo/testing.

***

## Spring Native Support
In order to build the native image, the following command can be used:

    mvn -Pnative spring-boot:build-image

The image can then be launched with Docker using the following command:

    docker run -p 8080:8080 \
    -e OPENAI_API_KEY=$OPEN_API_KEY \
    -e APP_USER_NAME=$APP_USER_NAME \
    -e APP_USER_PASSWORD=$APP_USER_PASSWORD \
    docker.io/library/summarizr:0.0.1-SNAPSHOT

Running the native image without security:

    docker run -p 8080:8080 \
    -e OPENAI_API_KEY=$OPEN_API_KEY \
    -e SECURITY_ENABLED=false 
    docker.io/library/summarizr:0.0.1-SNAPSHOT