
# Assignment 06 — Visitor Pattern

## Project Description

This project implements an in-memory hierarchical file system
using the Visitor Design Pattern.

The file system consists of files and directories.
Directories can contain files and other directories.

The project demonstrates Double Dispatch and separates
file system operations from the element classes.

## Design Patterns

Visitor Pattern is used to perform independent operations
on the same file system structure.

### Implemented Visitors

- TotalSizeVisitor — calculates the total size of all files.
- SearchVisitor — searches files by filename criteria
  and minimum size.
- StatisticsVisitor — calculates file count,
  directory count and maximum tree depth.

## Project Structure

- elements — file system elements.
- visitors — Visitor interface and implementations.
- Main.java — demonstration of the application.

## Technologies

- Java 17
- Maven
- JUnit 5

## How to Run

Run the application using Main.java.

Run all automated tests from the project root:

    mvnw test

On Windows PowerShell:

    .\mvnw.cmd test

## Testing

The project contains 16 automated tests.

All tests pass successfully:

- 16 tests run
- 0 failures
- 0 errors
- 0 skipped
