import org.anormcypher._

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

    readOrganizationFile();



    // create some test nodes
//    Cypher("""create (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute()

    // a simple query
//    val req = Cypher("start n=node(*) return n.name")
//
//    // get a stream of results back
//    val stream = req()
//
//    // get the results and put them into a list
//    println(stream.map(row => {row[String]("n.name")}).toList)

  }

  // Read User File line by line, insert data to correspond graph
  def readUserFile(): Unit ={
    var fileName = "/Users/yiucheungho/workspace/project2/src/main/scala/sample/user.csv"
    var flag: Boolean = false

    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/user.csv" AS csvLine
             CREATE (u:User { `User id`: csvLine.`User id`, `First name`: csvLine.`First name`, `Last name`: csvLine.`Last name` })""").execute()

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
    var fileName = "/Users/yiucheungho/workspace/project2/src/main/scala/sample/skill.csv"
    var flag: Boolean = false

    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/skill.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User Id` })
           CREATE UNIQUE (u)-[:haveSkill {`Skill level` : csvLine.`Skill level`}]-(x:Skill {Skill : csvLine.`Skill `})""").execute()




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
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/interest.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` })
           CREATE UNIQUE (u)-[:haveInterest {`Interest level` : csvLine.`Interest level`}]-(x:Interest {Interest : csvLine.`Interest`})""").execute()

  }


  // Read Organization File line by line, insert data to correspond graph
  def readOrganizationFile(): Unit ={
    Cypher("""LOAD CSV WITH HEADERS FROM "file:///Users/yiucheungho/workspace/project2/src/main/scala/sample/organization.csv" AS csvLine
             MATCH (u:User { `User id`: csvLine.`User id` })
           CREATE UNIQUE (u)-[:inOrganization]-(x:Organization {`organization type` : csvLine.`organization type`})""").execute()

  }

  // Read Direction File line by line, insert data to correspond graph
  def readDirectionFile(): Unit ={
    var fileName = "/Users/yiucheungho/workspace/project2/src/main/scala/sample/distance.csv"
    var flag: Boolean = false
    var org1: String = ""
    var org2: String = ""
    var dis: String = ""

    // Read each line
    for (line <- scala.io.Source.fromFile(fileName).getLines){
      val temp = line.split(',')

      // First line of csv, assign data
      if(flag == false ) {
        org1 = temp(0)
        org2 = temp(1)
        dis = temp(2)
        flag = true
      }
      // Write to graph
      else {
        System.out.println(temp(0), temp(1), temp(2))

        Cypher("""CREATE (n:Organization { name : {abc} })""").on("abc" -> temp(0)).execute()
      }
    }
  }
}
