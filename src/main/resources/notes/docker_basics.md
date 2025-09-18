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


## Restarting after changes

Stop all the containers -> Ctrl+c in terminal where docker-compose is running
Build the jar
Remove the images of local spring app and of docker-compose
docker-compose up --build

## First time
add Dockerfile
./mvnw clean package
docker build -t local-spring:1.0 . (can see the image built -> docker images)
docker run --rm local-spring:1.0 (--rm removes container when it exits)

needs database
