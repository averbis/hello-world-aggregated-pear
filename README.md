# hello-world-aggregated-pear [![Build Status](https://travis-ci.com/averbis/hello-world-aggregated-pear.svg?branch=master)](https://travis-ci.com/averbis/hello-world-aggregated-pear)

An Apache UIMA Hello World Annotator aggregating two annotators bundled as a PEAR package.

A PEAR package is a way of packaging text analysis components and their resources for use with the Apache UIMA
framework. Every PEAR can contain additional Java libraries and UIMA knows how to keep PEARs separate from each
other such that there are no problems if two different PEARs use incompatible versions of some library.

This project provides a minimal example how to build an aggregated analysis engine and how to bundle it as a self 
contained UIMA PEAR package.

## Build prerequisites

- JDK 1.8 or later
- Maven 

## Build instructions

    mvn clean install

The Maven build process performs of the following steps:

- Compile the two UIMA components `SimpleSentenceAnnotator` and SimpleTokenAnnotator` included in this project.
- Generate a UIMA aggregate analysis engine descriptor (pipeline) using the `GenerateAggregateAnalysisDescriptor` class
  included in this project. This pipeline becomes the top-level analysis component of the PEAR - i.e. what is executed
  when the PEAR is invoked by UIMA.
- Uses the Apache UIMA `PearPackagingMavenPlugin` to generate a PEAR file consisting of the two UIMA components included in this 
  project, all required dependencies (in particular the Averbis type system which is used by the components) and the 
  UIMA aggregate analysis engine descriptor generated in the previous step. The file is written to the `target` folder. 
- Executes the `PearPackageIntegrationTest` which uses Apache UIMA which temporarily installs the PEAR file, uses it to 
  process a sample document, and verifies that the expected `Token` and `Sentence` annotations have been created.