package com.zh.spark.day6_source

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @author zhanghe
  *        2019/4/4 17:24
  *
  *       MySQL作为数据源
  */
object JdbcSource {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JdbcSource").master("local[2]").getOrCreate()

    // 加载数据源
    val frame: DataFrame = sparkSession.read.format("jdbc").options(Map(
      "url" -> "jdbc:mysql://10.1.255.101:3306/urlcount",
      "driver" -> "com.mysql.jdbc.Driver",   //驱动
      "dbtable" -> "url_data",    //数据表
      "user" -> "root",
      "password" -> "root"
    )).load()
    //测试
    frame.printSchema()  //输出Schema
    frame.show() //显示关联的表数据

    // 过滤数据，查询id大于2的数据
    frame.filter(x => {
      x.getAs[Int](0) > 2
    })

  }

}
