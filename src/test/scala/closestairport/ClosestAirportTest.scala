package closestairport

import org.junit._
import org.junit.Assert._
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import com.esri.core.geometry.Point

@Test
class ClosestAirportTest {

  @Test
  def initializeData() {

    val sc = new SparkContext("local[*]", "test")
    val sqlContext = new SQLContext(sc)

    val airportDF = Utils.ingestCsvData(Constants.AIRPORT_DATA_PATH, sqlContext)
    assertEquals(airportDF.count(), Constants.AIRPORT_SIZE)
    
  }

  @Test
  def verifyDistance() {

    val airportDictionary: Array[(String, Point)] = Array(
      ("A", new Point(1.8, 1.8)),
      ("B", new Point(2.7, 2.7)),
      ("C", new Point(3.1, 3.1)))

    val user = new Point(3.0, 3.0)

    assertEquals(Utils.distance(user, airportDictionary), "C")

  }

}


