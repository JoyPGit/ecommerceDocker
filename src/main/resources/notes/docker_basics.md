Docker

docker images     -> localhost-spring
docker rmi<>  -f  -> ecom app
mvn clean install
docker build -t localhost:spring:1.0 .
docker-compose up --build


| Command                                | Description                                                                                    |
|----------------------------------------|------------------------------------------------------------------------------------------------|
| docker build .                         | builds an image from the Dockerfile, which can then be started as a container                  |
| docker build -t localhost-spring:1.0 . | *-t* -> tag -> any name with version; (.) specifies current folder where Dockerfile is present |
| docker images                          | lists all the images, check if the image has been created                                      |
| docker run <imageId>                   | Creates a *new* container every time                                                           |
| docker run -d <imageId>                | *-d* -> detached, Ctrl+c in terminal won't kill the container                                  |
| docker ps                              | all containers                                                                                 |
| docker ps -a                           | all containers (running as well as stopped)                                                    |
| docker start/stop <containerId>        | check the stopped containers with *docker ps* and restart them                                 |
| docker rmi <imageId>                   | to delete image                                                                                |
| docker rmi <imageId> -f                | force delete image                                                                             |
| docker logs                            | check logs                                                                                     |

---

Dockerfile

| base image                         | fetches java image from bellsoft/amazon                                                                                                            |
|------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| RUN                                | executes commands                                                                                                                                  |
| apk (Alpine Package keeper) update | downloads package index (database of available packages), when you try to install or upgrade, apk knows the latest available versions and metadata |
| fontconfig                         | it's a library that handles discovery and configuration of fonts, docker images are often minimal and don't include system fonts                   |
| ENV                                | environment variables                                                                                                                              |
| COPY <src> <dst>                   | copy the jar to destination                                                                                                                        |
| ENTRYPOINT                         | defines a command that will always run when a container starts from the image                                                                      |


## First time
add Dockerfile
./mvnw clean package
docker build -t local-spring:1.0 . (can see the image built -> docker images)
docker run --rm local-spring:1.0 (--rm removes container when it exits)

**Do you want this Dockerfile just to run a prebuilt JAR, or do you want it
to also build the JAR inside Docker (multi-stage build)?**
so docker-compose might build the project automatically

## Restarting after changes

Stop all the containers -> Ctrl+c in terminal where docker-compose is running
Build the jar
Remove the images of local spring app and of docker-compose
docker-compose up --build

needs database
Each app like postgres, redis, kafka has its own image and hence its own container
To manage these apps after running, an orchestrator is needed.
With a single command like `docker-compose up`, all defined services can be built,  
started and linked.

`-- build`
The --build flag instructs docker-compose to rebuild the images for any service that
has a build instruction in the docker-compose.yml file

project/
│── docker-compose.yml
│── Dockerfile
│── target/app.jar
│── db/
└── migrations/
├── V1__init.sql
├── V2__add_table.sql
