# closestAirport

### Project: Maven Project
### Language : Scala (2.10)
### Processing (Big Data) Framework : Apache Spark (1.6.0)
### Libraries: Spark-CSV, esri (Geo-Spatial)


This project requires 3 input arguments. First input argument is the path of airport data file, second argument is the path of users sample data file while third argument is the path where you want to dump the resultant processed data (preferably hdfs).

### How to run
1) You can clone this maven project and load it in eclipse and just by giving the required three argument run this program
2) You can build this project using "maven clean install" and run it using "spark-submit --class closestairport.Driver --master local[*] "jar-path" "airport-data-path" "user-data-path" "output-path" 

