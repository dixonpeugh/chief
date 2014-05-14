chief
=====

Chief is a program which builds an RDF knowledge base over a Java (and possibly other languages) project.  The goal of this knowledge base is to provide a launching pad for other - more interesting tasks.

Specifically, I would like the repository to have:
* Basic project information
* Structural information (packages, classes, methods, etc.)
* Dataflow information within a class
* Algorithmic information

Once we have some of this basic information down, we can use this as a springboard for more interesting analysis projects.  Specifically, I would like to tackle the following:
* Identification of Coding Smells which are out of reach of static source checking (like PMD)
* Identification of Software Patterns
* Identificaiton of work to swap in a third-party API.  (Or to recommend your API to a third party project.)
* Identification of areas of cut & paste - with recommended refactoring

Fully realized, this project has the potential to dramatically change the way in which we interact with legacy code.

For the first phase, I will be building an RDF representation of a program using te Source Code Resource Ontology (SCRO) at http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl



