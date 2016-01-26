package fr.ippon.spark.streaming

import com.cybozu.labs.langdetect.DetectorFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import twitter4j.auth.{Authorization, AuthorizationFactory}
import twitter4j.conf.{Configuration, ConfigurationContext}

/**
  * Created by ahars on 26/01/2016.
  */
class processTweets extends FlatSpec with Matchers with BeforeAndAfter {

  private var ssc: StreamingContext = _

  before {
    val sparkConf = new SparkConf()
      .setAppName("processTweets")
      .setMaster("local[*]")

    ssc = new StreamingContext(sparkConf, Seconds(2))
  }

  after {
    ssc.stop()
  }

  "" should "" in {

    // Twitter4J
    // IMPORTANT: ajuster vos clés d'API dans twitter4J.properties
    val twitterConf = ConfigurationContext.getInstance()
    val twitterAuth = Option(AuthorizationFactory.getInstance(twitterConf))

    // Jackson
    val mapper = new ObjectMapper()

    // Language Detection
    DetectorFactory.loadProfile("src/main/resources/profiles")

    val filters: Array[String] = Array("#Android")

    TwitterUtils.createStream(ssc, twitterAuth, filters)
        .foreachRDD(tweet => {
          tweet.collect().foreach(println)
        })


    ssc.start()
    ssc.awaitTermination()


  }
}
