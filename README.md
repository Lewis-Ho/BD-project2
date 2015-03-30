# BD-project2

Project II: User Case
collaborator.net is an application that enables users
to discover their own professional network, and
identify other users with particular skill sets. Users
work for organizations (governments, universities or
companies), work on projects, and have one or
more interests or skills that are weighted by their
significances or levels. Based on this information,
collaborator.net can
- describe a user’s professional network by
identifying other subscribers by a defined criteria
- identify collaborators with specific skills who are
directly or indirectly connected to the current user
Project II: Requirement
• Build a database to model collaborator.net
• At least five entities for each entity-type or relations for each
relation-type.
• The database should at least answer the following questions in
a quick response time:
– For a university user, find all other individuals who share the same
interests or skills as the user, and work in the same or different
organization within 10 miles from the organization that the user
works. The individuals should be ranked by the total weight of
shared interests (or skills) with the user. In addition, the output
should include the organization name, and the list of common
interests (or skills).
– For a user, find all trusted colleagues-of-colleagues who have one or
more particular interests. The “trusted colleague” is defined as two
persons have worked on the same project.
• A Scala command-line client interface for database creation
and query (other language e.g. Python or Java is acceptable)
Project II: Requirement
• Document (in print!)
– Design diagram
– All queries
– Potential improvements (e.g. how to speed up
query)
• All source codes (sent by email)
• Two-person team
• Due: 11:59pm, April 12 (Just after Spring
Break)

Here are more detailed specifications on the project 2.


1. You need to write three programs in one of the following languages: scala, java, python, or C++, although scale is prefered. The first program called DbLoader will load the data into a database. The second and third programs are called QueryCollabrator and QueryColOfCol, respectively.


2. The input for DbLoader is a fold that includes six files for the data. In project fold of the blackboard, there is a compressed sample data fold. All you input data should use the same file names, headers, and formats. Although only a few records in the sample data, I will test your programs using ~1 million records.


3. The input of QueryCollaborator is a user id and a distance. The outputs are a list of user names, their common interests (skills) shared with the query user, ranked the interest (skill) weights. See lecture notes for more details.


4. The input of QueryColOfCol is a user id. The outputs are a list of user names. See lecture nodes for more details.