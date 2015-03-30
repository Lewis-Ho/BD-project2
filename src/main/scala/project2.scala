import org.anormcypher._

object project2 {

  // Setup the Rest Client
  implicit val connection = Neo4jREST()

  def main(args:Array[String]):Unit = {

    // Get line, split elements and write to array[object]
//    val lines = scala.io.Source.fromFile("file.txt").toString
//    println("What is the file name?")
    var fileName = "/Users/yiucheungho/workspace/project2/src/main/scala/sample/distance.csv"
    for (line <- scala.io.Source.fromFile(fileName).getLines){
      val temp = line.split(',')

      System.out.println(temp(0))
//      val temp= line.split(",")
////      System.out.println(temp.)
//      temp.foreach{
//
//      }
//      val obj = new Node(temp(0).toFloat, temp(1))  // Write to new object
//      //find min and replace... tomorrow
//      x.enqueue(obj)
    }


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