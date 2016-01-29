package fr.ippon.spark.ml

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

/**
  * Created by ahars on 29/01/2016.
  */
class ProcessTitanic extends FlatSpec with Matchers with BeforeAndAfter {

  private var sc: SparkContext = _
  private var sqlc: SQLContext = _

  before {
    val sparkConf = new SparkConf()
      .setAppName("processTitanic")
      .setMaster("local[*]")

    sc = new SparkContext(sparkConf)
    sqlc = new SQLContext(sc)
  }

  after {
    sc.stop()
  }

  "ML - Apprentissage supervisé : Prédiction du nombre de survivants du Titanic" should "send results to ElasticSearch" in {

    // Récupération des données
    val trainDf = Titanic.dataframeFromTitanicFile(sqlc, "src/main/resources/data/titanic/train.csv")
    val testDf = Titanic.dataframeFromTitanicFile(sqlc, "src/main/resources/data/titanic/test.csv")
    val expectedDf = Titanic.dataframeFromTitanicFile(sqlc, "src/main/resources/data/titanic/gendermodel.csv")

    println
    println("Jeu d'entrainement")
    trainDf.show()

    println
    println("Jeu de test")
    testDf.show()

    println
    println("Label à trouver")
    expectedDf.show()

    println
    println("----------------------------------------------")
    println

    // Data Cleansing

    // Correction des valeurs erronées sur l'age des passagers
    val meanAge = Titanic.calcMeanAge(trainDf, "Age")

    println("Age moyen calculé : " + meanAge)

    val trainWithCorrectionsDf = Titanic.fillMissingAge(trainDf, "Age", "Age_cleaned", meanAge)
    val testWithCorrectionsDf = Titanic.fillMissingAge(testDf, "Age", "Age_cleaned", meanAge)

    println
    println("Jeu d'entrainement")
    trainWithCorrectionsDf.show()

    println
    println("Jeu de test")
    testWithCorrectionsDf.show()

    println
    println("----------------------------------------------")
    println



  }

}
