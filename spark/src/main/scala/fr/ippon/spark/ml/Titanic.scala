package fr.ippon.spark.ml

import org.apache.spark.sql.SQLContext

/**
  * Created by ahars on 29/01/2016.
  */
object Titanic {

  def dataframeFromTitanicFile(sqlc: SQLContext, file: String) = sqlc.read
    .format("com.databricks.spark.csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load(file)

}
