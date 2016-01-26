package fr.ippon.spark.streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

/**
  * Created by ahars on 26/01/2016.
  */
class processTweets extends FlatSpec with Matchers with BeforeAndAfter {

  private var sc: SparkContext = _

  before {
    val sparkConf = new SparkConf()
      .setAppName("processTweets")
      .setMaster("local[*]")

    sc = new SparkContext(sparkConf)
  }

  after {
    sc.stop()
  }

  "" should "" in {

  }
}
