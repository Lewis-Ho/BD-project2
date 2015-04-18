# Collaborator

This project also served as the purpose of demonstrating the basic setup for scala - neo4j env on Intellij and the power of neo4j on describing nodes and relationships in between.
Detail of query please look into the specific file on src/main/scala/

Collaborator is an application that enables users
to discover their own professional network, and
identify other users with particular skill sets. Users
work for organizations (governments, universities or
companies), work on projects, and have one or
more interests or skills that are weighted by their
significances or levels. 

If you have trouble on setting up neo4j on scala, you can follow the build.sbt to setup yours project.
Notice that the build have to be on Intellij. 

Sample file was provided src/main/scala/. This app was built based on the file.

# Requirement

Scala 2.11, Neo4j, Intellij IDEA 14
Scala - Neo4j drive AnormCypher: http://www.anormcypher.org/

# Usage

Create Nodes and Relationships
- run src/main/scala/DbLoader.scala to create data, will need to specify the path (Notice sample text file was include also)

Query users based on skill and distance of organization. (Or within specific distance)
- run QueryCollabrorator.scala

Query on colleagues-of-colleagues. (User's user's user)
- run src/main/scala/QueryColOfCol.scala 

# Note
Please credit me if this repo helps you somehow :) 
