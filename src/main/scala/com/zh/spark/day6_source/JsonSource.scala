package com.zh.spark.day6_source

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author zhanghe
  * 2019/4/4 17:44
  */
object JsonSource {

  def main(args: Array[String]): Unit = {
    //创建sparkSession
    val sparkSession = SparkSession.builder().appName("JsonSource").master("local[2]").getOrCreate()

    //读取Json数据源
    val jread: DataFrame = sparkSession.read.json("d:/saveJson")

    import sparkSession.implicits._
    // 处理数据
    val fread: Dataset[Row] = jread.filter($"xueyuan" === "bigdata")
    // 触发Action
    fread.show()
    // 关闭资源
    sparkSession.stop()
  }


}
