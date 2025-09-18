### ecommerceDocker

## Overview

- Live API         | [Swagger spec](http://localhost:8000/swagger-ui/index.html#/)
- API Design       | [API Docs](<Doc Link>) `(to be added later)`
- Tech Stack       | `Java`, `SpringBoot`, `Postgres`, `Flyway`, `Redis`, `Kafka`, `Docker`

## Description : 'e-commerce'

Sporing boot service for e-commerce application. This application includes 
these major flows

1 Onboarding of users <br>
2 SKU Management <br>
3 Warehouse inbounding of orders upon inspection <br>
4 Purchase Requisitions for order placed for inbound goods and creation of 
purchase order <br>
5 Purchase order procesing includes payment and delivery flow <br> 

Detailed description : [confluence_doc]


# Architecture
## C$ Component (Level 3) Diagram

---

## Modules

Modules are created in the modules package in `src/main/**/modules`. Each module follows 
a clean architecture pattern with domain, service, controller and infrastructure layers.


|Module         | Description | Status      |
|---------------|-------------|-------------|
|`example`      | Sample business domain module with CRUD operations| Active |
|`e com`        | e commerce service and business logic             | WIP    |
<br>

---


## Project Structure and Patterns

> This project follows modular design principles with a separation between domain logic, services and controllers and infrastructure to ensure clear ownership and maintainability.


### Folder/File descriptions
> Top level folders and important files

| Folder / File                | Purpose                                              |
|`/mocks`                      | Postman collections and environments for API testing |
|`/templates`                  | Kubernetes deployment templates                      |
|`src/test`                    | Unit and architectural tests                         |
|`src/main/**/modules/example` | Example business logic module                        |
|`src/main/**/common`          | Cross-cutting concerns (e.g. exceptions, utilities   |
|`Dockerfile`                  | Docker container definition                          |
|`docker-compose.yml`          | Docker based local dev setup                         |
|`pom.xml`                     | Maven build configuration                            |
|`application*.properties`     | Environment-specific configurations                  |

---
# Getting started
### Prerequisites
