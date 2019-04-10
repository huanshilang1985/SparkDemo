package com.zh.spark.day6_source

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author zhanghe
  * 2019/4/4 17:48
  *
  * 读取Csv数据源
  */
object CsvSource {

  def main(args: Array[String]): Unit = {
    // 创建sparkSession
    val sparkSession = SparkSession.builder().appName("CsvSource").master("local[2]").getOrCreate()
    //2.读取json数据源
    val read: DataFrame = sparkSession.read.csv("d:/saveCsv")

    import sparkSession.implicits._
    // 处理数据
    val rdf: DataFrame = read.toDF("id","xueyuan")
    val rs: Dataset[Row] = rdf.filter($"id" <= 3)
    // 执行Action
    rs.show()
    // 释放资源
    sparkSession.stop()
  }


}
