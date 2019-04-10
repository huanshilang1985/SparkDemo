package com.zh.spark.day6_source

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author zhanghe
  *  2019/4/4 22:03
  */
object ParquetSource {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("ParquetSource").master("local[2]").getOrCreate()
    // 读取parquet格式数据
    import sparkSession.implicits._
    val parframe: DataFrame = sparkSession.read.parquet("d:/savePar")
    //输出schema信息
//    parframe.printSchema()
//    parframe.show()

    // 计算（需求：过滤出学院是java的）
    val jrs: Dataset[Row] = parframe.filter($"xueyuan" === "java")
    //执行
    jrs.show()
    // 关闭资源
    sparkSession.stop()
  }

}
