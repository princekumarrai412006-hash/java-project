# StudyHub — Java Study Planner

## Overview
StudyHub is an original Java console application for students who need one place to manage courses, plan assignments, and understand their workload. It is designed as a VITyarthi Build Your Own Project for the Java syllabus and demonstrates Java fundamentals, object-oriented programming, exception handling, collections, multithreading, JDBC, and JPA.

## Functional modules
1. **Course and task management:** create courses, add tasks, list records, mark tasks complete, and delete courses through a JDBC repository.
2. **Progress analytics:** calculate total, completed, pending, and planned study minutes using Java streams and a fixed thread pool.
3. **Persistence and reporting:** initialize an H2 relational schema, perform CRUD operations with JDBC, and provide JPA entities plus a persistence configuration for ORM-based CRUD.

## Technology stack
Java 21, Maven, H2 Database, JDBC, Jakarta Persistence API, Hibernate ORM, JUnit 5, Mermaid diagrams, Git.

## Project structure
```text
src/main/java/com/vityarthi/studyhub/
├── app/Main.java                 # application workflow and menu
├── dao/Database.java             # JDBC connection and schema
├── dao/StudyRepository.java      # CRUD repository
├── model/Course.java             # JPA entity and OOP model
├── model/Task.java
├── model/TaskStatus.java
└── service/
    ├── AnalyticsService.java     # collections + multithreading
    ├── JpaService.java           # JPA lifecycle demonstration
    └── StudyHubException.java
```

## Run
Prerequisites: Java 21 and Maven 3.9+.

```bash
mvn clean test
mvn exec:java
mvn exec:java -Dexec.args=interactive
```

The default run seeds two courses and two tasks into an in-memory H2 database. The interactive mode exposes list, completion, and analytics operations. The database is intentionally in-memory so every run is isolated and reproducible.

## Testing
`mvn test` runs unit tests for task state transitions, validation, and concurrent analytics. The repository layer uses prepared statements and structured exception translation via `StudyHubException`.

## Design artefacts and report
- [Project statement](statement.md)
- [Architecture and UML diagrams](docs/diagrams.md)
- [Detailed project report](docs/project-report.md)
- [PDF report](docs/project-report.pdf)

## Git workflow
```bash
git init
git add .
git commit -m "Build StudyHub Java project"
```
