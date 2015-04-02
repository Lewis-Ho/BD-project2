import org.anormcypher._
import project2._

object QueryCollabrorator {
  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  def main(args:Array[String]):Unit = {
    //flag for quit loop
    var exit = false


    do{
      println("What is the user id?")
      val userId = Console.readLine().toString

      println("What is the distance?")
      val distance = Console.readLine().toString

//      val result = Cypher(
//        """
//        MATCH (i:Interest)-[r]-(User{`User id` : {userId}}),
//        (i)-[m]-(u:User),
//        (u)-[]-(o: Organization)
//        RETURN u.`First name`, u.`Last name`, i.Interest, o.organization
//        ORDER BY m.`Interest level`
//        """).on("userId" -> userId)

      val result = Cypher(
        """
          MATCH (Organization)-[r :inDistance]-(o: Organization),
          (o)-[]-(u: User),
          (u)-[x]-(s: Skill),
          (u)-[w]-(i : Interest)
          WHERE r.Distance <= {dis}
          RETURN u.`First name`, u.`Last name` , r.`Distance`, s.Skill, x.`Skill level` , i.Interest, w.`Interest level`
        """).on("userId" -> userId, "dis" -> distance)

      //Transform the resulting Stream[result] to a List[] Optional
      val resultList = result.apply().map(row =>
        row[String]("u.`First name`") -> row[String]("u.`Last name`") -> row[String]("i.Interest")
      ).toList

      // Print output
      System.out.println(resultList)

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
