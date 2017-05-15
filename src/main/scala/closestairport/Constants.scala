package closestairport

import org.apache.spark.sql.types._

/**
 * @author shahroz
 */
object Constants {
  
  val SCHEMA = StructType(Array(StructField("uuid", StringType, true), StructField("iata_code", StringType, true)))
  val AIRPORT_DATA_PATH: String = """resources/optd-sample-20161201.csv"""
  val USERS_DATA_PATH: String = """resources/sample_data.csv"""
  val AIRPORT_SIZE : Long = 6889 
  val USERS_SIZE: Long = 1000000
}