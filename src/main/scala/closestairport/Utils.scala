package closestairport

import com.esri.core.geometry.GeometryEngine
import com.esri.core.geometry.Point
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.DataFrame

/**
 * @author shahroz
 */
object Utils extends Serializable{

  def ingestCsvData(filePath: String, sqlContext: SQLContext): DataFrame = {

    sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(filePath)

  }

  def createAirportDictionary(airportDF: DataFrame): Array[(String, Point)] = {

    airportDF.map(line =>
      (line(0).toString, new Point(line(2).toString.toDouble, line(1).toString.toDouble))).collect()

  }

  def distance(userGeo: Point, airportDictionary: Array[(String, Point)]) = {

    var min = Double.MaxValue
    var iataCode = ""

    airportDictionary.foreach {
      case (airportIATA, airportGeo) =>
        val distance = GeometryEngine.geodesicDistanceOnWGS84(userGeo, airportGeo)
        if (distance < min) {
          min = distance
          iataCode = airportIATA
        }
    }
    iataCode
  }

  def dumpCsvData(destinationPath: String, df: DataFrame) {

    df.coalesce(1).write
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .save(destinationPath)

  }

}