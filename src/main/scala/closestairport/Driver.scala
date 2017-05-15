package closestairport

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import com.esri.core.geometry.Point
/**
 * @author shahroz
 */
object Driver {

  def main(args: Array[String]): Unit = {

    if (args.size != 3) {
      println("invalid arguments")
      return
    }

    val sc: SparkContext = new SparkContext("local[*]", "ClosestAirport")
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    // Ingesting CSV Datasets
    val airportDF = Utils.ingestCsvData(args(0), sqlContext)
    val userDF = Utils.ingestCsvData(args(1), sqlContext)

    // Creating Airport in-memory structure wich will be broadcasted
    val airPortDictionary = sc.broadcast(Utils.createAirportDictionary(airportDF))

    // finding closest airport to every user (RDD)
    val userAirportRDD = userDF.map(line =>
      Row.fromSeq(
        Seq(
          line(0).toString,
          Utils.distance(new Point(line(2).toString.toDouble, line(1).toString.toDouble), airPortDictionary.value))))

    // applyiing required schema on RDD
    val userAirportDF = sqlContext.createDataFrame(userAirportRDD, Constants.SCHEMA)

    // Logging the results for viewing
    userAirportDF.show(100)

    // dumping dataframe as csv
    Utils.dumpCsvData(args(2), userAirportDF)

  }

}