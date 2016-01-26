package fr.ippon.spark.streaming

import com.cybozu.labs.langdetect.DetectorFactory
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import twitter4j.auth.AuthorizationFactory
import twitter4j.conf.ConfigurationContext

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

  "Process Tweets with #Android" should "collect them from Twitter, print and store into ElasticSearch" in {

    // Twitter4J
    // IMPORTANT: ajuster vos clés d'API dans twitter4J.properties
    val twitterConf = ConfigurationContext.getInstance()
    val twitterAuth = Option(AuthorizationFactory.getInstance(twitterConf))

    // Language Detection
    DetectorFactory.loadProfile("src/main/resources/profiles")
    val lang = new LangProcessing

    val filters: Array[String] = Array("#Android")

    TwitterUtils.createStream(ssc, twitterAuth, filters)
        .map(json => Tweet(json.getUser.getName(), json.getText(), json.getCreatedAt(), lang.detectLanguage((json.getText()))))
        .foreachRDD(tweet => tweet.foreach(println))

    ssc.start()
    ssc.awaitTermination()
  }
}
