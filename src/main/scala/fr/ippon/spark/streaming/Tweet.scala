package fr.ippon.spark.streaming

import java.util.Date

/**
  * Created by ahars on 26/01/2016.
  */
case class Tweet(user: String, text: String, createdAt: Date, language: String)
