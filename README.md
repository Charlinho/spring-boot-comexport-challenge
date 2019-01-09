# Spring Boot - Comexport Challenge

## Installing

Run `mvn install`.

## Run App

Run `mvn package && java -jar target/comexport-1.0.jar
` in root project folder.

## Run with Docker

Run `mvn package docker:build` to build and execute tests.

### Container

When build has been finished, 

Run `docker images`.

Copy IMAGE ID from spring-comexport image.

Run `docker run -it -p 8080:8080 <container_id>`.

Enjoy!!

## APIs


| URL                          | Method    | 
| :--------------------------- | ---------:|
|  /lancamentos-contabeis      | POST      |
|  /lancamentos-contabeis/{id} | GET       |