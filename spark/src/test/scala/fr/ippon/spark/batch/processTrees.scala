package fr.ippon.spark.batch

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

/**
  * Created by ahars on 28/01/2016.
  */
class processTrees extends FlatSpec with Matchers with BeforeAndAfter {

  private var sc: SparkContext = _
  private var sqlc: SQLContext = _

  before {
    val sparkConf = new SparkConf()
      .setAppName("processTrees")
      .setMaster("local[*]")

    sc = new SparkContext(sparkConf)
    sqlc = new SQLContext(sc)
  }

  after {
    sc.stop()
  }

  "RDD : Comptage des arbres de Paris par espèce" should "print results" in {

    sc
      .textFile("src/main/resources/data/tree/arbresalignementparis2010.csv")
      .filter(line => !line.startsWith("geom"))
      .map(line => line.split(";", -1))
      .map(fields => (fields(4), 1))
      .reduceByKey(_+_)
      .sortByKey()
      .foreach(t => println(t._1 + " : " + t._2))
  }
}
