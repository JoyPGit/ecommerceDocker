1. spring boot version can be checked via starter-parent version in pom.xml <br>
2. mvn not found -> ./mvnw works, why?
maven wrapper exists in project, maven might not be locally installed
Spring Initializr automatically includes a Maven Wrapper when generating a Maven-based Spring Boot project.


1.1 maven build fails -> comment out @SpringBootTest
1.2 mvn clean package vs build?

| mvn package                                            | mvn build                                             |
|--------------------------------------------------------|-------------------------------------------------------|
| Compiles the code, runs tests then packages the result | Does everything that package does plus it             |
| into an artifact (like JAR, WAR, etc.).                | installs the artifact into the local Maven repository |
| Builds the atrifact in target/ folder                  |                                                       |

mvn package
 
2. start with Docker, no postgres installed locally

`2.1` add docker file

**Do you want this Dockerfile just to run a prebuilt JAR, or do you want it
to also build the JAR inside Docker (multi-stage build)?**

[+] Running 1/1
✔ Container flyway  Recreated                                                                                                0.1s
Attaching to flyway, kafka, postgres, redis, zookeeper


`2.2` how to get jar without version in name?
In your pom.xml, add this under <build>:

<build>
    <finalName>myapp</finalName> -> ${project.artifactId}
</build>
This is required for specifying jar in Dockerfile

<br>

`2.3` Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
Need docker-compose file to add postgres, kafka, redis

Need docker compose
generated a docker compose without entry for spring boot app

`docker-compose up --build          
zsh: command not found: docker-compose`

brew install docker-compose

brew command not found, zsh keeps forgetting brew <br>
` *run these commands* ` <br>

/opt/homebrew/bin/brew doctor  <br>
echo 'export PATH="/opt/homebrew/bin:$PATH"' >> ~/.zshrc <br>
source ~/.zshrc <br>
brew install docker-compose

`2.4` To connect local spring boot app to docker postgres?
<br>
Use same creds as in dbeaver <br>
**Remove the maven dependency, refresh pom, run app
add dependency, reload

_Also remove the driver-class-name, it's not reqd_
**
<br>
<https://stackoverflow.com/a/78149005/5667980>
<br>
`Q` : does postgres by default connect with this? <br>
```
SPRING_DATASOURCE_USERNAME: myuser
SPRING_DATASOURCE_PASSWORD: mypassword
```

Yes — as per the docker-compose.yml, Postgres will accept those credentials
because we explicitly set them in the postgres service’s environment variables:
```
  postgres:
    image: postgres:15
    environment:
      POSTGRES_USER: myuser
      POSTGRES_PASSWORD: mypassword
      POSTGRES_DB: mydb
```
<br>

`2.5 Q` If I include my local spring boot app service in docker-compose like this
```yaml
    services:
      ecom-service:
        build: .
        platform: linux/amd64
        container_name : ecom-service
        ports:
          - "4500:4322"
```

This will bring up the docker server on port 4322, whose url will be 4500.
> So either connect the local spring boot app to docker containers
> via specifying in application.properties <br>
> OR use the docker container of your service at the port specified

<br>


`2.8` Check via terminal and UI (for db) if entries are being made

`2.8` `How to add profiles/env for docker?`

`2.9` `Why configmap.yaml?` <br>
required for K8s <br>
Inside Spring Boot, they match application.properties entries like:
```
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

<br>

3. `POSTGRES`
`3.1` default value for postgres column?
`3.2` postgres needs psql?
`3.3` brew install --cask dbeaver-community
files required by driver in dbeaver -> Download
connect using url `jdbc:postgresql://localhost:5432/mydb`
Factory method 'dataSource' threw exception with message: Cannot load driver class: org.postgresql.Driver
> add dependency
```
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```
`3.4` Check if app is running on port 8080

`3.5` If you already added the dependency but still get this error in Docker, 
issue might be that app is already runing via `app:` key specified in docker-compose



<br>
mvn clean package spring-boot:repackage
vs mvn clean install
<br>

```
<goals>
    <goal>repackage</goal>
</goals>
vs <goals>check?
```

<br>
4. swagger <br>
5. K8s <br>
6. redis <br>
7. kafka <br>
8. spotless and its hook <br>
9. logging <br>
10. lombok <br>

add lombok plugin to IntelliJ
Without the plugin:
> IntelliJ can’t “see” the generated code (e.g., getters, setters, constructors, etc.). <br>
> You’ll see false positives like: "Cannot resolve method", "Field is never assigned" <br>
>  Missing method or field errors — even if the code compiles fine.

Add the dependency and enable annotation processing
> Preferences / Settings → Build, Execution, Deployment → Compiler → Annotation Processors


11. jasypt <br>
12. flyway <br>
13. profiles (local Dockerfile) <br>

> @ActiveProfiles("test")  // why?
    "When running this test, use the application-test.yml or application-test.properties configuration instead of the default."

14. transactions <br>
15. jmeter
16. sonar
17. datadog?


started with docker-compose up --build
Your app service uses build:
This tells Docker Compose to build a custom image for the springboot-app from your local project using your Dockerfile.
container name for app in docker-compose doesn't matter 

have connected to postgres via dbeaver
health endpoint working
working spring boot app in docker container, will try connecting local
app to docker containers for db, kafka, redis etc NEXT

app or any name in docker-compose doesn't matter, it's just a placeholder

docker-compose up --build uses the old image as build only builds 
containers from images




_LOGS_
Once the server starts, check logs in Docker UI

15. FLYWAY
type baseline, version 1, checksum null

flyway in docker compose not picking up schema
check path in volumes : - ./src/main/resources/db/migration:/flyway/sql

FAT JAR
it might mean your 
Dockerfile is only copying target/app.jar but you didn’t build a fat JAR (with dependencies).
check and comment out app in docker-compose, it might be building local spring boot app image

works locally too by commenting out the app/service in docker-compose


dev-tools?
lombok mapstruct annotationProcessPaths required in pom.xml

regexp "^[]" */+ $ ?

why @JsonProperty

all changes in entity should be effected via flyway, not hibernate
ddl-auto = none

use alter script to change entity after initial draft

@NotNull vs NonNull
sql -> UUID NOT NULL UNIQUE DEFAULT
gen_random_uuid()

create index
add logs table for audit

after restarting docker, dbeaver datasource was invalidated
disconnect the db and connect [OR] Close dbeaver and restart
How to Prevent This
Regularly restarting containers	It's OK, but keep using a named volume (postgres_data) so data persists

user_type_enum doesn't exist -> add enums in script

flyway always shows baseline and no version update, remove baseline in application.properties
`Adding baselineOnMigrate=true to the migrate command can silently cause Flyway to skip older migrations.`

You dropped the flyway_schema_history table, and now Flyway won't recreate it?



```
Your commerce schema already has existing tables (or objects), but Flyway has never managed it before — there’s no 
Flyway schema history table (flyway_schema_history) to track migrations. 

It refuses to run migrations on a non-empty schema without baseline info, to avoid messing up existing data.
You need to tell Flyway where to start from by baselining.
```

Steps
> 1 Add this in docker-compose under flyway
```
      - -schemas=commerce
      - -createSchemas=true
      - -baselineOnMigrate=true
```
> 2 ~~add spring.flyway.baseline-on-migrate=true in application.properties~~ <br>
> why? if you are running the app locally, docker will pick values from docker-compose,
> **so any changes to application.properties don't matter**
> Docker builds the image from compose and changes to docker-compose are independent of spring boot app changes <br>
> 
> 3 comment out the property in docker-compose

does flyway craete new schema everytime with this flag? in docker-compose
- -createSchemas=true
  ChatGPT said:

Great question.

No, Flyway does not create a new schema every time with -createSchemas=true.

---
mapstruct

needs 2 core and processor
Mapstruct is an annotation processor
w/o processor no code is generated
annotationProcessorPaths -> maven-compiler-plugin 
-> avoid conflicts and ensures correct ordering when using with lombok

when using lombok+mapstruct -> if you override default compiler plugin behavior
by setting annotationProcessorPaths, 
-> include both lombok and mapstruct processor entries

on project ecommerce: Resolution of annotationProcessorPath dependencies failed: version can neither be null, empty nor blank -> [Help 1]

**lombok requires enabling annotation processing how???**
> Go to Preferences > Build, Execution, Deployment > Compiler > Annotation Processors
>✅ Ensure "Enable annotation processing" is checked

mapstruct works with an interface, the processor
if mapping is null with mapstruct, lombo isnt generating getters and setters
Lombok + MapStruct binding, which can be tricky because Lombok generates methods at compile time, and MapStruct relies on those generated methods to perform field mapping.

If the setup is wrong, MapStruct won’t “see” Lombok-generated getters/setters, and the mapper will fail to bind fields — just like you're experiencing.
also the mapperImpl generated can be checked at target/generated-sources/annotations/UserMapperImpl

don't forget to add this. why? @JsonProperty causes the names to change and
hence without this it doesn't bind

```
<path>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-mapstruct-binding</artifactId>
    <version>0.2.0</version>
</path>
```

without this mapstruct doesn't identify Mppaer interface
<scope>provided</scope>

[This dependency is needed to compile the code, but it will be provided at runtime by the environment (e.g., the 
application server or build tool). 
So don’t package it into the final artifact (like a JAR or WAR).]

[mapstruct-processor is only needed during compilation to generate the mapper classes. 
It's not needed at runtime. Marking it as provided keeps your final build clean 
and avoids unnecessary dependencies in your runtime classpath.]

For validation checks this is reqd
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

business_name if empty is not considered as null, hence @NotNull doesn't catch it.
For strings use @NotBlank or @NotEmpty. NoTBlank checks for white spaces as well


wareHouseTags -> add column name in entity as hibernate converts wareHouseTags to ware_house_tags  while it's 
defined as warehouse_tags in dto and entity

And most importantly: your DTO class is using @JsonProperty(...) to rename fields, which can cause field-name mismatches if your UserEntity uses the original field names.


What @RequestBody Does:

> Converts the JSON/XML body of a request into a Java object.
> Uses HttpMessageConverters under the hood to perform the conversion (like MappingJackson2HttpMessageConverter for 
> JSON) for content-type =application/json
> Typically used in @PostMapping, @PutMapping, etc., where there’s a request payload.
> use in controller, if used in service layer, service would be coupled to HttpMessageConverter machinery

path variable vs request param
added valid in controller

org.postgresql.util.PSQLException: ERROR: null value in column "is_deleted" of relation "users" violates not-null 
constraint??
even after setting default in postgres script, still hibernate overrides it.
So always set default val in entity, but make changes for variables not 
present in DTO like isDeleted, set default value in mapper using constant
[@Mapping(target = "status", constant = "ACTIVE")]

Why add constraints in sql script if hibernate overrides it?
For integrity during manual entries

don't forget to build after mapstruct changes

[org.springframework.web.HttpMediaTypeNotAcceptableException: No acceptable representation]

how slf4j works?

without @Service too, there is no compile error

@SpringBootTest -> contextLoads fails when docker is not running
it needs to connect to db, why?
If spring boot uses db (specified in application.properties), the context is set
and it looks for the same context in test, Hence h2
-> try with h2??

Cannot load driver class: org.h2.Driver
means Spring Boot is trying to use H2 (in-memory database), but the H2 JDBC driver 
is not on the classpath — i.e., the H2 dependency is missing from your pom.xml.
classpath -> pom.xml

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

Or, if you also want to run the app locally with H2 (not just for tests), remove the <scope>test</scope>:

but it has issues with flyway, so disable it




e2e tests via test Container


[All about tests]

@SpringBootTest needs db?

Yes — by default, @SpringBootTest does require a working database if your application is configured to use one 
(like PostgreSQL via Spring Data JPA).
What is this config? if you specify datasource in application.properties

✅ Why?
@SpringBootTest loads the entire Spring ApplicationContext, including:

All beans (repositories, services, controllers, etc.)
Spring Boot auto-configurations (like JPA, Flyway, Liquibase, etc.)

The database connection (if configured)
So if you have something like this in application.yml:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=myuser
spring.datasource.password=mypassword
```


➡️ Spring Boot will try to connect to that DB when the test starts.

🔧 What if you don’t want a real DB for tests?
✅ Option 1: Use an in-memory database like H2 for tests

Use @ActiveProfiles("test") and define application-test.properties with H2:

[_src/test/resources/application-test.properties_]

Then annotate your test class:
```java
@SpringBootTest
@ActiveProfiles("test")
public class SomeServiceTest {
// test runs using H2

}
```

✅ Option 2: Mock database dependencies

If your test doesn't need a real DB connection, use:
@WebMvcTest, @DataJpaTest, or @MockBean to load partial context

Or use unit tests without Spring context at all


Controller mockmvc test
check thr imports carefully 
jsonPath mockMvcResultMatchers
MockBean is required with WebMvcTest

Use @MockBean in your Spring Boot tests when you want Spring to manage and inject the mock.

Ignore @MockitoBean — it’s not an official or recognized annotation.

Example using @WebMvcTest:

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    // No DB needed!
}

✅ Option 3: Use Testcontainers (with Docker)

If you want real DB behavior in tests but don’t want to use a shared Postgres instance, you can use Testcontainers
to spin up a containerized Postgres for your test.

But this requires Docker and fails if Docker is not running.


### **Can i use h2 for e2e tests?** 
<br> 
Yes, you absolutely can use H2 for end-to-end (E2E) tests — and it’s a very common practice.

But whether you should depends on what kind of coverage and realism you need from your tests.

✅ When it makes sense to use H2 for E2E tests

H2 is fast, lightweight, and in-memory, so it’s great when:

✅ You want fast E2E test runs without Docker or external services.
✅ You don't rely on Postgres-specific features (e.g., native JSONB, sequences, specific types).
✅ Your application uses JPA/Hibernate in a portable way (i.e., not tightly bound to Postgres).

⚠️ When using H2 for E2E can be dangerous
❌ You're using PostgreSQL-specific features, such as:
jsonb, uuid, or array columns
Native SQL queries with ILIKE, ::cast, etc.
citext, tsvector, custom types

❌ Your tests pass in H2 but fail in real Postgres

❌ You want true production fidelity

---
### Unit Tests

Skip integration tests during install

If you only want to run unit tests and skip integration tests with Testcontainers during install, mark them separately.

✅ Use Maven profile for integration tests:

Tag integration tests using a custom JUnit tag:

@Tag("integration")
@Testcontainers
@SpringBootTest
public class EcommerceIntegrationTest {
// ...
}


Configure Maven Surefire and Failsafe plugins in your pom.xml:

<build>
  <plugins>

    <!-- Unit tests -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.5.3</version>
      <configuration>
        <excludedGroups>integration</excludedGroups>
      </configuration>
    </plugin>

    <!-- Integration tests -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-failsafe-plugin</artifactId>
      <version>3.1.2</version>
      <executions>
        <execution>
          <goals>
            <goal>integration-test</goal>
            <goal>verify</goal>
          </goals>
          <configuration>
            <groups>integration</groups>
          </configuration>
        </execution>
      </executions>
    </plugin>

  </plugins>
</build>


Why Use maven-surefire-plugin?
🔹 By default:

Maven implicitly uses maven-surefire-plugin for unit tests during the test phase.
But the default version might be outdated, especially if you're using JUnit 5 or custom settings.

<version>3.1.2</version> <!-- Use 3.x for JUnit 5 -->
When You Should Configure It Explicitly

Using JUnit 5	                        ✅ Yes — needs Surefire 3.x+
Custom test includes/excludes         	✅ Yes
Run tests in parallel               	✅ Yes
Control test timeouts or system props	✅ Yes
Run Only Specific Tests	            	✅ Yes

<configuration>
  <includes>
    <include>**/*Test.java</include>
  </includes>
  <excludes>
    <exclude>**/IntegrationTest.java</exclude>
  </excludes>
</configuration>


Then run:

# Skip integration tests
mvn clean install

# OR run with integration tests
mvn clean install -Pwith-integration

```java

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceUnitTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;
}


```

@SpringBootTest in unite test will cause issue. Why? 

@SpringBootTest loads the full Spring context.

But you're using Mockito annotations (@Mock, @InjectMocks), which work outside of Spring context.

@InjectMocks is a Mockito feature — not Spring-aware — and doesn’t inject Spring beans like UserMapper.

get rid of @SpringBootTest and @ActiveProfiles

testUserEntity is null




// ❌ Remove this
// @Mock
// private UserEntity testUserEntity;

// ✅ Instead, create a real instance in your test method
private UserEntity testUserEntity;

By default, fields like UUID userId are null, and setUserId() does nothing unless stubbed.
You’re trying to use it like a normal object, which it’s not.

Spring Boot 3.1+ typically includes Mockito 5.x.

Without `@ExtendWith(MockitoExtension.class)`

Your @Mock or @InjectMocks fields will be null
Tests will throw NullPointerException
You'll have to call MockitoAnnotations.openMocks(this) manually


**static constant util -> create an instance and hold in static var and reuse it**

---


ObjectMapper is for java <-> json
Will add Gson later for handling Instant



--- 
### Test Container

org.springframework.dao.InvalidDataAccessResourceUsageException: could not prepare statement 
[Schema "COMMERCE" not found; SQL statement: ->
> 1 tries to connect to h2?
> 2 Is the PostgreSQLTestContainer properly implemented and started? <br>
Pulling docker image: testcontainers/ryuk:0.12.0. Please be patient; this may take some time but only needs to be done once.

> 3 is db initialized properly? <br>
> Is it possibler that h2 is still being used? <br>
> remove h2 properties in application-test.properties <br>
You cannot customize the container startup via .withInitScript() when using @ServiceConnection because 
> Spring manages the container internally. <br>
Instead, initialize your schema using schema.sql/data.sql files, Flyway/Liquibase migrations,
> **REMEMBER** Testcontainers do NOT depend on Flyway.
> or programmatically run SQL commands on the DataSource. <br>
These SQL scripts will be executed after the container & datasource are ready.


But commenting h2 properties causes the context to not find any db.
Trying by spinning up docker container for postgres, but will local
spring boot app connect to it?
yes because i hadn't added activeprofiles = test, so is it possible it was picking from application,properties?

how can having test containers but not having h2 in application-test.properties work with spring context?

When you use Testcontainers (like PostgreSQL) in your integration tests, the datasource configuration typically comes from your Spring properties (e.g., application-test.properties).
If you do NOT include H2 (or any in-memory DB) config in application-test.properties, Spring Boot will expect you to provide a real database configuration (like PostgreSQL) either:

Directly in your application-test.properties or

Programmatically, e.g., via Testcontainers integration or dynamic properties.


How does Spring Boot wiring work with Testcontainers then?
Testcontainers usually spins up a container dynamically in your test code.

You then inject those dynamic connection details (URL, username, password) into Spring’s datasource config 
before the context loads — typically via: **@DynamicPropertySource**

@ServiceConnection?

Introduced in Spring Boot 3.2+, @ServiceConnection is an annotation designed to automatically bind 
a Testcontainer or any external service connection to your application’s configuration.

It reduces boilerplate by automatically wiring your container’s connection details into your Spring environment 
without needing @DynamicPropertySource or manual property overrides.

TO make @SpringBootTest work w/o h, use @Testcontainers
and specify a postgres connection inside the test class
```
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>(
            DockerImageName.parse(PostgreSQLContainer.IMAGE).withTag("16-alpine"));
```
