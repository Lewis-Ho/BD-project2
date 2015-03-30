import org.anormcypher._


object project2 {

  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  def main(args:Array[String]):Unit = {

    // create some test nodes
    Cypher("""create (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute()

    // a simple query
    val req = Cypher("start n=node(*) return n.name")

    // get a stream of results back
    val stream = req()

    // get the results and put them into a list
    println(stream.map(row => {row[String]("n.name")}).toList)

  }
}