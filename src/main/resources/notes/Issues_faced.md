1. mvn not found -> ./mvnw works, why?
maven wrapper exists in project, maven might not be locally installed

1.1 maven build fails -> comment out @SpringBootTest
1.2 mvn clean package vs build?

| mvn package                                            | mvn build                                             |
|--------------------------------------------------------|-------------------------------------------------------|
| Compiles the code, runs tests then packages the result | Does everything that package does plus it             |
| into an artifact (like JAR, WAR, etc.).                | installs the artifact into the local Maven repository |
| Builds the atrifact in target/ folder                  |                                                       |

mvn package
 
2. start with Docker, no postgres installed locally
add docker file
and then docker compose

**Do you want this Dockerfile just to run a prebuilt JAR, or do you want it
to also build the JAR inside Docker (multi-stage build)?**

2.1 how to get jar without version in name?
In your pom.xml, add this under <build>:

<build>
    <finalName>myapp</finalName> -> ${project.artifactId}
</build>
This is required for specifying jar in Dockerfile

2.2 Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
Need docker-compose file to 

4. swagger
5. K8s
6. redis
7. kafka
8. spotless and its hook
9. logging
10. lombok
11. jasypt
12. flyway
13. profiles (local Dockerfile)
14. transactions
