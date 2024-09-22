# Spring Boot 3 & Spring Modulith

![Coverage](.github/badges/jacoco.svg)

This repository contains a simple TODO application built using Spring Boot 3 and Spring Modulith, with a PostgreSQL database running on Docker. The project is designed to demonstrate the power of modular monolith architecture by breaking down the application into distinct modules that are loosely coupled and follow the principles of domain-driven design. It leverages Docker and Docker Compose to streamline the setup and deployment of both the application and the database.

## Features
* **Spring Boot 3**: Java framework.
* **Spring Modulith**: Implements modular monolithic architecture for better separation of concerns.
* **Events**: Using ApplicationEventPublisher to enable event-driven communication between modules.
* **PostgreSQL**: Relational database for persistent data storage.
* **Docker Compose**: Orchestration tool for running the app and PostgreSQL together with one command.
* **JUunit 5**: Unit testing framework.

## Architecture Overview
The application is divided into the following modules, using Spring Modulith:

* **Gateway Module**: Acts as the entry point, routing API requests to the appropriate modules.
* **Employee Module**: Manages employee data and operations.
* **Task Module**: Handles all business logic related to creating and managing tasks.
* **Assignment Module**: Links employees to specific tasks, handling task assignments and ownership.

The PostgreSQL database is used for persistent storage, managed via Docker.

## UML Diagram
The following UML diagram illustrates how the different modules in this TODO application interact with each other:

![components_puml](https://github.com/user-attachments/assets/43efbbef-c7af-485b-96a3-9d8f1848a916)

## Running the Application with Docker Compose:
1. **Clone the repository**:
   
   ```console
   git clone https://github.com/istvankuli/spring-boot-modulith.git
   cd spring-boot-modulith
   ```
3. **Set env variables**: Example .env file:
   ```console
   DATABASE_USER=user
   DATABASE_PASSWORD=password
   ```
    
3. **Start the application**: Run the following command to start both the Spring Boot application and PostgreSQL database using Docker Compose:
   
   ```console
   docker-compose up
   ```

