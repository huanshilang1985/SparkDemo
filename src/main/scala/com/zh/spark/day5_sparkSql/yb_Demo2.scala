package com.zh.spark.day5_sparkSql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 使用Case class来创建DataFrame
  */
object yb_Demo2 {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val path: String = getClass.getClassLoader.getResource("user.txt").getFile
    val spark: SparkSession = SparkSession.builder().appName("yb_Demo2").master("local[2]").getOrCreate()

    val userRdd: RDD[Array[String]] = spark.sparkContext.textFile(path).map(_.split(" "))

    val userDataFrame: RDD[Student] = userRdd.map(x => Student(x(0).toInt, x(1), x(2).toInt))

    import spark.sqlContext.implicits._
    val userDF: DataFrame = userDataFrame.toDF()

    userDF.createOrReplaceTempView("t_user2")
    spark.sql("select * from t_user2").show
    spark.stop()
  }

}

case class Student(stuId:Int,stuName:String,stuAge:Int)

//+-----+-------+------+
//|stuId|stuName|stuAge|
//+-----+-------+------+
//|    1|zhanghe|    18|
//|    2|   lili|    19|
//|    3|   reba|    20|
//|    4|   gogo|    34|
//|    5|laowang|    25|
//|    6|  beizi|    24|
//+-----+-------+------+