# Springboot- Articles- Microservice


Minimal [Spring Boot](http://projects.spring.io/spring-boot/) Sample Articles service.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

# Apis

- `/api/v1/private/articles`
- `/api/v1/private/articles/{articleId}`

# Documentation

Swagger documentation is available on `http://localhost:8802/swagger-ui.html`


## Building the application locally


To build your app and install dependencies, you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

Make sure that the `core` (https://github.com/MahmoudEssam24/microservice-core) jar is generated.

In the core project directory
```shell
"<path to project directory>/core/mvnw" clean -f "<path to project directory>/core/pom.xml"
"<path to project directory>/core/mvnw" install -f "<path to project directory>/core/pom.xml"
```

In articles project directory
```shell
"<path to project directory>/articles/mvnw" clean -f "<path to project directory>/articles/pom.xml"
"<path to project directory>/articles/mvnw" install -f "<path to project directory>/articles/pom.xml"
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `sa.com.me.article` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn run
```

## Running the application locally using dockers

### Requirements

install docker daemons on your machine, you can find all the steps here: (https://docs.docker.com/docker-for-mac/)

TO start the application using docker, there is a Dockerfile in the root directory of the application. Navigate to root directory and in your terminal run the following:

```shell
"<path to project directory>/articles/mvnw" clean -f "<path to project directory>/articles/pom.xml"
"<path to project directory>/articles/mvnw" install -f "<path to project directory>/articles/pom.xml"
docker image build -t article-service .
```

That will create your image, then you run your container using:

```shell
docker container run  -it --name article -p 8802:8802 -d article-service java -jar articles.jar --eureka.client.serviceUrl.defaultZone=http://<Eureka Container Ip>:8761/eureka/
```

To get `Eureka Container Ip` After starting Eureka container, run this command:

```shell
docker inspect <containerNameOrId> | grep '"IPAddress"' | head -n 1
```

`containerName` in our case is `eureka`
