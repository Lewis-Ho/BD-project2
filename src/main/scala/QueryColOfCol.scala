import org.anormcypher.CypherParser._
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

      val result:List[(String,String)] = Cypher(
        """
          MATCH (User{`User id` : {userId}})-[]-(p: Project),
          (p)-[]-(u: User),
          (u)-[]-(x: Project),
          (x)-[]-(y: User)
          RETURN y.`First name`, y.`Last name`
        """)
        .on("userId" -> userId)
        .as((str("y.`First name`") ~ str("y.`Last name`") )
        .map(flatten).*)

      println("\nAll trusted colleagues-of-colleagues who have one or more particular interests:  \n")
      var listLen = 0
      if ( result.length > 0 ){
        for (a <- listLen to result.length - 1){
          println("First Name: " +  result(a)._1  + "   Last Name: " + result(a)._2 )
        }
      } else {
        println("Sorry, there is no trusted colleagues-of-colleagues who have one or more particular interests. ")
      }

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
