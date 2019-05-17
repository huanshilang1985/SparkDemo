package com.zh.spark.day5_sparkSql

import java.util.Properties

import com.zh.spark.day5_sparkSql.yb_Demo2.getClass
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * 把操作结果写入mySQL
  */
object yb_Demo3mysql {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val spark: SparkSession = SparkSession.builder().appName("yb_Demo3mysql").master("local[2]").getOrCreate()
    val path: String = getClass.getClassLoader.getResource("user.txt").getFile

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
    userDataFrame.createOrReplaceTempView("t_user3")

    val result: DataFrame = spark.sql("select * from myperson")

    result.show()

    //把结果存入到mysql中
    val props = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "123456")
    //追加模式
    result.write.mode("append").jdbc("jdbc:mysql://localhost:3306/company?serverTimezone=UTC&characterEncoding=utf-8", "student", props)
    spark.stop()
  }

}
