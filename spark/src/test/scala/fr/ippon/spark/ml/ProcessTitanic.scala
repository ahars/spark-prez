package fr.ippon.spark.ml

import org.apache.spark.ml.{PipelineModel, Pipeline}
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer}
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

    // Features Engineering

    // Conversion de la colonne "Survived" en colonne numérique "Label" utilisée par l'algorithme de ML
    val labelIndexModel = new StringIndexer()
      .setInputCol("Survived")
      .setOutputCol("label")
      .fit(trainWithCorrectionsDf)

    val trainWithLabelDf = labelIndexModel.transform(trainWithCorrectionsDf)

    println
    println("Jeu d'apprentissage")
    trainWithLabelDf.show()

    // Conversion de la colonne "Sex" en colonne numérique "Sex_indexed"
    val sexIndexModel = new StringIndexer()
      .setInputCol("Sex")
      .setOutputCol("Sex_indexed")

    //
    val vectorizedFeaturesModel = new VectorAssembler()
      .setInputCols(Array("Pclass", "Sex_indexed", "Age_cleaned"))
      .setOutputCol("features")

    // Instanciation de l'algorithme supervisé de ML des Random Forests implémenté dans Spark ML
    val randomForestAlgo = new RandomForestClassifier()

    println
    println("----------------------------------------------")
    println

    // Pipeline Building
    val pipeline = new Pipeline()
      .setStages(Array(
        sexIndexModel,
        vectorizedFeaturesModel,
        randomForestAlgo
      ))

    // Training of the Model
    val model = pipeline.fit(trainWithLabelDf)

    println("Le Modèle de ML généré par l'algorithme des Random Forests")
    println(model.asInstanceOf[PipelineModel].stages(2).asInstanceOf[RandomForestClassificationModel].toDebugString)

    println
    println("----------------------------------------------")
    println
  }
}
