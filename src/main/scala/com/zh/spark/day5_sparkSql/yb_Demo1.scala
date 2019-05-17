package com.zh.spark.day5_sparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.log4j.Logger
import org.apache.log4j.Level

/**
  * yibo 练习题
  * 2019/4/22 17:20
  */
object yb_Demo1 {

  def main(args: Array[String]): Unit = {
    //设置日志输出内容
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //文件路径
    val path: String = getClass.getClassLoader.getResource("user.txt").getFile
    val spark: SparkSession = SparkSession.builder().appName("yb_Demo1").master("local[2]").getOrCreate()

    val userRdd: RDD[Array[String]] = spark.sparkContext.textFile(path).map(_.split(" "))

    //通过StructType定义Schema
    val schema: StructType = StructType(
      List(
        StructField("id", IntegerType),
        StructField("name", StringType),
        StructField("age", IntegerType)))
    //将RDD映射到rowRDD上，映射到schema上
    val rowRDD: RDD[Row] = userRdd.map(p => Row(p(0).toInt, p(1), p(2).toInt))
    val userDataFrame: DataFrame = spark.createDataFrame(rowRDD, schema)
    //注册视图
    userDataFrame.createOrReplaceTempView("t_user")

    spark.sql("select * from t_user order by age desc").show()
    spark.stop()

  }

//  +---+-------+---+
//  | id|   name|age|
//  +---+-------+---+
//  |  4|   gogo| 34|
//  |  5|laowang| 25|
//  |  6|  beizi| 24|
//  |  3|   reba| 20|
//  |  2|   lili| 19|
//  |  1|zhanghe| 18|
//  +---+-------+---+



}
