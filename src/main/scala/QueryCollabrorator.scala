import org.anormcypher.CypherParser._
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
      val distance = Console.readLine().toInt

      // Share the same skill, in same organization
      val SSresult:List[(String,String,String, String, String, String)] = Cypher(
        """
          MATCH (u: User)-[x: haveSkill]-(s: Skill)-[y: haveSkill]-(us: User),
                (u)-[]-(o: Organization)-[]-(us: User)
          WHERE u.`User id` = {userId}
          RETURN us.`First name`, us.`Last name`, s.Skill, x.`Skill level`, y.`Skill level`, o.organization
          ORDER BY y.`Skill level`
        """)
        .on("userId" -> userId)
        .as((str("us.`First name`") ~ str("us.`Last name`") ~ str("s.Skill") ~ str("o.organization") ~ str("x.`Skill level`")  ~ str("y.`Skill level`"))
        .map(flatten).*)

      println("\nUser who share the same skill, in same organization: \n")
      var SSListLen = 0
      if ( SSresult.length > 0 ){
        for (a <- SSListLen to SSresult.length - 1){
          var maxNum = 0
          var aN = SSresult(a)._5.toInt
          var bN = SSresult(a)._6.toInt
          //          // Calculate common skill level max(wji, wki)
          if ( aN < bN){
            maxNum = bN
          } else {
            maxNum = aN
          }

          println("First Name: " +  SSresult(a)._1  + "   Last Name: " + SSresult(a)._2 + "   Interest: " + SSresult(a)._3 + "    Organization: "  + SSresult(a)._4 + "   Common Skill Level: "  + maxNum)
        }
      } else {
        println("Sorry, there is no user who is share the same skill, in same organization. ")
      }

      // Share the same skill, in diff organization within 10 miles
      val SDresult:List[(String,String,String, String, String, String)] = Cypher(
        """
          MATCH (u: User)-[x: haveSkill]-(s: Skill)-[y: haveSkill]-(us: User),
                (u)-[]-(o: Organization),
                (us)-[]-(diffO: Organization),
                (o)-[r :inDistance]-(diffO)
          WHERE r.Distance <={dis} AND u.`User id` = {userId}
          RETURN us.`First name`, us.`Last name`, s.Skill, x.`Skill level`,y.`Skill level`, o.organization
          ORDER BY x.`Skill level`
        """)
        .on("userId" -> userId, "dis" -> distance)
        .as((str("us.`First name`") ~ str("us.`Last name`")  ~ str("s.Skill") ~ str("o.organization")~ str("x.`Skill level`")  ~ str("y.`Skill level`"))
        .map(flatten).*)

      println("\nUser who share the same skill, in diff organization within 10 miles: \n")
      var SDListLen = 0
      if ( SDresult.length > 0 ){
        for (a <- SDListLen to SDresult.length - 1){
          var maxNum = 0
          var aN = SDresult(a)._5.toInt
          var bN = SDresult(a)._6.toInt
          //          // Calculate common skill level max(wji, wki)
          if ( aN < bN){
            maxNum = bN
          } else {
            maxNum = aN
          }

          println("First Name: " +  SDresult(a)._1  + "   Last Name: " + SDresult(a)._2 + "   Interest: " + SDresult(a)._3 + "    Organization: "  + SDresult(a)._4 + "   Common Skill Level: "  + maxNum)
        }
      } else {
        println("Sorry, there is no user who is share the same skill, in diff organization within 10 miles. ")
      }


      // Share the same interest, in same organization
      val ISresult:List[(String,String,String, String, String, String)] = Cypher(
        """
          MATCH (u: User)-[x: haveInterest]-(s: Interest)-[y: haveInterest]-(ui: User),
                (u)-[]-(o: Organization)-[]-(ui: User)
          WHERE u.`User id` = {userId}
          RETURN ui.`First name`, ui.`Last name`, s.Interest, x.`Interest level`, y.`Interest level`, o.organization
          ORDER BY y.`Interest level`
        """)
        .on("userId" -> userId)
        .as((str("ui.`First name`") ~ str("ui.`Last name`")~ str("i.Interest") ~ str("o.organization") ~ str("x.`Interest level`") ~ str("y.`Interest level`"))
        .map(flatten).*)

      println("\nUser who share the same interest, in same organization: \n")
      var ISListLen = 0
      if ( ISresult.length > 0 ){
        for (a <- ISListLen to ISresult.length - 1){
          // Calculate Common Interest Weight, wji+wki
          var ciw = 0
          var aN = ISresult(a)._5.toInt
          var bN = ISresult(a)._6.toInt

          ciw = aN + bN

          println("First Name: " +  ISresult(a)._1  + "   Last Name: " + ISresult(a)._2 + "   Interest: " + ISresult(a)._3 + "    Organization: "  + ISresult(a)._4 + "    Common Interest Weight: " + ciw)
        }
      } else {
        println("Sorry, there is no user who is share the same interest, in same organization. ")
      }

      // Share the same interest, in diff organization within 10 miles
      val IDresult:List[(String,String,String, String, String, String)] = Cypher(
        """
          MATCH (u: User)-[x: haveInterest]-(i: Interest)-[y: haveInterest]-(ui: User),
                (u)-[]-(o: Organization),
                (ui)-[]-(diffO: Organization),
                (o)-[r :inDistance]-(diffO)
          WHERE r.Distance <= {dis} AND u.`User id` = {userId}
          RETURN ui.`First name`, ui.`Last name`, i.Interest, x.`Interest level`,y.`Interest level` , o.organization
          ORDER BY y.`Interest level`
        """)
        .on("userId" -> userId, "dis" -> distance)
        .as((str("ui.`First name`") ~ str("ui.`Last name`") ~ str("i.Interest") ~ str("o.organization") ~ str("x.`Interest level`")  ~ str("y.`Interest level`"))
        .map(flatten).*)

      println("\nUser who share the same interest, but in different organization within 10 miles: \n")
      var IDListLen = 0
      if ( IDresult.length > 0 ){
        for (a <- IDListLen to IDresult.length - 1){
          // Calculate Common Interest Weight, wji+wki
          var ciw = 0
          var aN = IDresult(a)._5.toInt
          var bN = IDresult(a)._6.toInt

          ciw = aN + bN

          println("First Name: " +  IDresult(a)._1  + "   Last Name: " + IDresult(a)._2 + "   Interest: " + IDresult(a)._3 + "    Organization: "  + IDresult(a)._4 + "    Common Interest Weight: " + ciw)
        }
      } else {
        println("Sorry, there is no user who is share the same interest, but in different organization within 10 miles. ")
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
