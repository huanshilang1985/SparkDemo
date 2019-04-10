package com.zh.spark.day6_source

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author zhanghe
  *  2019/4/4 17:36
  *
  *  连接MySQL数据源，操作数据
  */
object JdbcSource1 {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JdbcSource1").master("local[2]").getOrCreate()

    import sparkSession.implicits._
    // 加载数据
    val urlData: DataFrame = sparkSession.read.format("jdbc").options(Map(
      "url" -> "jdbc:mysql://10.1.255.101:3306/zhanghe",
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> "url_data",
      "user" -> "root",
      "password" -> "root"
    )).load()

    val r: Dataset[Row] = urlData.filter($"id" > 2)
    val rs : DataFrame = r.select($"xueyuan",$"number_one")

    //写入以text格式，只能写一列
    //rs.write.text("d:/saveText")

    //写入以json格式
    //rs.write.json("d:/saveJson")

    //写入以csv
    //rs.write.csv("d:/saveCsv")

    rs.write.parquet("d:/savePar")

    rs.show()
    sparkSession.stop()
  }

}
