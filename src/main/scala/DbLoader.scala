import org.anormcypher._
import org.anormcypher.CypherParser._

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

  } // End Main Def

  // Read User File line by line, insert data to correspond graph
  def readUserFile(): Unit ={
    println("What is the path of the User file?")
    val userfile = Console.readLine().toString

    // Create the node first if user node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM {userfile} AS csvLine
           MERGE (u:User { `User id`: csvLine.`User id`, `First name`: csvLine.`First name`, `Last name`: csvLine.`Last name` })""").on("userfile" -> userfile).execute()

  }

  // Read Skill File line by line, insert data to correspond graph
  def readSkillFile(): Unit ={
    println("What is the path of the Skill file?")
    val skillfile = Console.readLine().toString

    // Create the node first if skill node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM {skillfile} AS csvLine
           MERGE (x:Skill {Skill : csvLine.`Skill `})""").on("skillfile" -> skillfile).execute()

    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM {skillfile} AS csvLine
             MATCH (u:User { `User id`: csvLine.`User Id` }),
             (x:Skill {Skill : csvLine.`Skill `})
           CREATE UNIQUE (u)-[:haveSkill {`Skill level` : csvLine.`Skill level`}]-(x)""").on("skillfile" -> skillfile).execute()

  }

  // Read Skill File line by line, insert data to correspond graph
  def readInterestFile(): Unit ={
    println("What is the path of the Interest file?")
    val interestfile = Console.readLine().toString

    // Create the node first if interest node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM {interestfile} AS csvLine
           MERGE (x:Interest {Interest : csvLine.`Interest`})""").on("interestfile" -> interestfile).execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM {interestfile} AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }) ,
             (x:Interest {Interest : csvLine.`Interest`})
           CREATE UNIQUE (u)-[:haveInterest {`Interest level` : csvLine.`Interest level`}]-(x)""").on("interestfile" -> interestfile).execute()

  }


  // Read Organization File line by line, insert data to correspond graph
  def readOrganizationFile(): Unit ={
    println("What is the path of the Organization file?")
    val orgfile = Console.readLine().toString

    // Create the node first if organization node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM {orgfile} AS csvLine
           MERGE (x:Organization {organization: csvLine.organization ,`organization type` : csvLine.`organization type`})""").on("orgfile" -> orgfile).execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM {orgfile} AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }),
             (x:Organization {organization: csvLine.organization ,`organization type` : csvLine.`organization type`})
           CREATE UNIQUE (u)-[:inOrganization]-(x)""").on("orgfile" -> orgfile).execute()

  }

  // Read Project File line by line, insert data to correspond graph
  def readProjectFile(): Unit ={
    println("What is the path of the Project file?")
    val projfile = Console.readLine().toString

    // Create the node first if project node is not exist
    Cypher("""LOAD CSV WITH HEADERS FROM {projfile} AS csvLine
           MERGE (x:Project {Project : csvLine.Project})""").on("projfile" -> projfile).execute()
    // Create relationship with nodes
    Cypher("""LOAD CSV WITH HEADERS FROM {projfile} AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` }),
             (x:Project {Project : csvLine.Project})
           CREATE UNIQUE (u)-[:inProject]-(x)""").on("projfile" -> projfile).execute()

  }

  // Read Direction File line by line, insert data to correspond graph
  def readDirectionFile(): Unit = {
    println("What is the path of the Distance file?")
    val distfile = Console.readLine().toString

    Cypher( """LOAD CSV WITH HEADERS FROM {distfile} AS csvLine
             MATCH (u:Organization { `organization`: csvLine.`Organization 1` }),
             (x: Organization { `organization`: csvLine.`Organization 2` })
           CREATE UNIQUE (u)-[:inDistance {Distance : toInt(csvLine.Distance)}]-(x)""").on("distfile" -> distfile).execute()
  }
}
