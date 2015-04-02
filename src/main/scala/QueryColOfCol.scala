import org.anormcypher._

object QueryColOfCol {
  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  def main(args:Array[String]):Unit = {
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
          RETURN y.`First name`, y.`Last name`
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
  }
}
