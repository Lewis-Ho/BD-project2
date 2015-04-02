import org.anormcypher._
import QueryCollabrorator._

object project2 {

  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  def main(args:Array[String]):Unit = {

    // Read User File
    readUserFile();
    // Read Skill File
    readSkillFile();
    // Read Interest File
    readInterestFile();
    // Read Organization File
    readOrganizationFile();
    // Read Project File
    readProjectFile();
    // Read Direction File
    readDirectionFile();

    //flag for quit loop
    var exit = false

    do {
      println("What is the user id?")
      val userId = Console.readLine().toString

      val result = Cypher(
        """
          MATCH (User{`User id` : {userId}})-[]-(p: Project),
          (p)-[]-(u: User),
          (u)-[]-(x: Project),
          (x)-[]-(y: User)
          RETURN  y.`First name`, y.`Last name`
        """).on("userId" -> userId)


      //Transform the resulting Stream[result] to a List[] Optional
      val skillList = result.apply().map(row =>
        row[String]("y.`First name`") -> row[String]("y.`Last name`")
      ).toList

      // Print output
      System.out.println(skillList)


      // Input validation, ask if user need another query
      println("More query?   Y for more query / N to quit the program.")
      // Flag for input validation
      var valid = false

      do {
        val choice = Console.readLine().toString

        if (choice == "Y" || choice == "y") {
          println("Continue... ")
          valid = true
          exit = false
        }
        else if (choice == "N" || choice == "n") {
          println("Exiting the program... See you next time!")
          valid = true
          exit = true
        }
        else {
          println("Input doesn't recognize, please choose either Y/N or y/n")
          valid = false
        }
      } while (valid == false)
    } while( exit == false)
  } // End Main Def

  // Read User File line by line, insert data to correspond graph
  def readUserFile(): Unit ={
    // Create the node first if user node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/user.csv" AS csvLine
           MERGE (u:User { `User id`: csvLine.`User id`, `First name`: csvLine.`First name`, `Last name`: csvLine.`Last name` })""").execute()


    // Read each line
//    for (line <- scala.io.Source.fromFile(fileName).getLines){
//      val temp = line.split(',')
//
//      // First line of csv, assign data
//      if(flag == false ) {
//        flag = true
//      }
//      // Write to graph
//      else {
//        Cypher("""CREATE (n:User { 'User id' : {id}, 'First name' : {fn}, 'Last name' : {ln} })""").on("id" -> temp(0), "fn" -> temp(1), "ln" -> temp(2)).execute()
//      }
//    }
  }

  // Read Skill File line by line, insert data to correspond graph
  def readSkillFile(): Unit ={
    // Create the node first if skill node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/skill.csv" AS csvLine
           MERGE (x:Skill {Skill : csvLine.`Skill `})""").execute()

    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/skill.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User Id` }),
             (x:Skill {Skill : csvLine.`Skill `})
           CREATE UNIQUE (u)-[:haveSkill {`Skill level` : csvLine.`Skill level`}]-(x)""").execute()




//    // Read each line
//    for (line <- scala.io.Source.fromFile(fileName).getLines){
//      val temp = line.split(',')
//
//      // First line of csv, assign data
//      if(flag == false ) {
//        flag = true
//      }
//      // Write to graph
//      else {
//        System.out.println(temp(0), temp(1), temp(2))
//
//        val hasSkill = Cypher(" start n=node(*) where n.Name = {sk} return n.name as name " ).on("sk" -> temp(1))
//
//        // Transform the resulting Stream[CypherRow] to a List[(Int)] Optional
//        val skillList = hasSkill.apply().map(row =>
//          row[Option[Int]]("name")
//        ).toList
//
//        System.out.println(skillList )
//
//        // Create skill if it doesn't exist, else do nothing
//        if ( skillList.length == 0 ) {
//          System.out.println("creating")
//          Cypher("""CREATE (n:Skill { Name : {sk}})""").on("sk" -> temp(1)).execute()
//        } else {
//          // Nothing
//        }
//
//        // Create relationship between the user node with skill node, plus skill level as property
//        Cypher("""
//          MATCH (user { User_id: {id} }), (skill {Name : {sk} })
//          CREATE UNIQUE (user)-[r:X { level: {sk_l} }]-(skill)
//          RETURN r
//               """).on("id" -> temp(0), "sk" -> temp(1), "sk_l" -> temp(2).toInt).execute()
//
////          val query = "MATCH WHERE user1.User_id = {id}, skill1.Name = {sk} CREATE user1-[:haveSkill]->skill1"
////          val req: CypherStatement = Cypher(query).on("id" -> temp(0), "sk" -> temp(1), "sk_l" -> temp(2))
//
//      }
//    }
  }

  // Read Skill File line by line, insert data to correspond graph
  def readInterestFile(): Unit ={
    // Create the node first if interest node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/interest.csv" AS csvLine
           MERGE (x:Interest {Interest : csvLine.`Interest`})""").execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/interest.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }) ,
             (x:Interest {Interest : csvLine.`Interest`})
           CREATE UNIQUE (u)-[:haveInterest {`Interest level` : csvLine.`Interest level`}]-(x)""").execute()

  }


  // Read Organization File line by line, insert data to correspond graph
  def readOrganizationFile(): Unit ={
    // Create the node first if organization node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/organization.csv" AS csvLine
           MERGE (x:Organization {organization: csvLine.organization ,`organization type` : csvLine.`organization type`})""").execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/organization.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }),
             (x:Organization {organization: csvLine.organization ,`organization type` : csvLine.`organization type`})
           CREATE UNIQUE (u)-[:inOrganization]-(x)""").execute()

  }

  // Read Project File line by line, insert data to correspond graph
  def readProjectFile(): Unit ={
    // Create the node first if project node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/project.csv" AS csvLine
           MERGE (x:Project {Project : csvLine.Project})""").execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/project.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }),
             (x:Project {Project : csvLine.Project})
           CREATE UNIQUE (u)-[:inProject]-(x)""").execute()

  }

  // Read Direction File line by line, insert data to correspond graph
  def readDirectionFile(): Unit = {
    Cypher( """LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/distance.csv" AS csvLine
             MATCH (u:Organization { `organization`: csvLine.`Organization 1` }), (x: Organization { `organization`: csvLine.`Organization 2` })
           CREATE UNIQUE (u)-[:inDistance {Distance : toInt(csvLine.Distance)}]-(x)""").execute()
  }
}
