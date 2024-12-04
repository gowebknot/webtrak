# Bellboy Service Template

This is a Spring boot application which is used as a base service for all the new bellboy microservices.  
For all the new microservices, we take a clone of this service.

## Steps to set up a new project in Local Machine
1. Install IntelliJ in Local Machine [See here](https://www.jetbrains.com/help/idea/installation-guide.html)
2. Import the project. (File -> Open -> Select project)
3. Set the Tab size/ Indentation for the project by following this path.
   ```File -> Settings -> Editor -> Code Style -> Java```
   In the `Tabs and Indents` tab, enter value as 2 for `Tab Size`, `Indent` and `Continuation Indent`.
4. To define Environment Variables in IntelliJ.
   ```Run -> Edit Run Configuration ->`+` icon -> gradle```
   Define all the project details, Main class path and Environment Variables and click on Apply button.
5. Command to clean up the project.
   ``` ./gradlew clean build```
6. Command to run the project.
   ``` ./gradlew bootRun```

## Steps to set up a new project/microservice with this template
**prerequisite:** Java 17

1. Rename the packages and classes.
2. Update the `settings.gradle` file (To update the service name).
3. Update the `build.gradle` file (To update the description).
4. If  your service needs database connection, then update `application.yml`  and  `build.gradle` files accordingly. Refer to **Section 1** for more details.
5. Remove the unnecessary files and add required files.

## Section 1: Adding DB and packages

* To add DB, update the `application.yml` file.

```  yml
 spring: 
  datasource: 
   driverClassName: org.postgresql.Driver 
   url: jdbc:postgresql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME} 
   username: ${DATABASE_USERNAME} 
   password: ${DATABASE_PASSWORD}
```  

* New libraries can be added via `build.gradle` file.
```  java
ext {
   // Add a version variable
   springVersion = "2.4.0"
}  
  
dependencies {  
   // Add the library in correct format
   implementation "org.springframework.boot:spring-boot-starter-data-jpa:${springVersion}" 
}
 ```  

## Section 2: Logging
* For enabling logging just go with the instructions in logback.xml under src/main/resources.
* If not enabled you will only get ERROR level logs as your class inherits from root logger which is ancestor of all the loggers.
<https://www.baeldung.com/logback#:~:text=Logback%20Architecture&text=A%20logger%20is%20a%20context,have%20more%20than%20one%20Appender.>

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogExample {
    public void someMethod() {
        log.info("Doing stuff");
    }
}
```