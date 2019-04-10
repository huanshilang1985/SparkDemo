package com.zh.spark.day5_sparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * @author zhanghe
  * 2019/4/3 13:50
  *
  * spark2.x  SparkSQL
  * 把查询结果，封装成表格，使用SQL操作
  */
object SqlTestSQL {

  def main(args: Array[String]): Unit = {
    //文件路径
    val path: String = getClass.getClassLoader.getResource("user.txt").getFile

    // 构建SparkSession
    val sparkSession: SparkSession = SparkSession.builder()
      .appName("SqlTestSQL")
      .master("local[2]")
      .getOrCreate()
    // 创建rdd, path也可以是hdfs路径
    val rdd1: RDD[String] = sparkSession.sparkContext.textFile(path)
    // 切分数据
    val rdd2: RDD[Array[String]] = rdd1.map(_.split(" "))
    // 封装数据
    val rowRdd: RDD[Row] = rdd2.map(x => {
      val id = x(0).toInt
      val name = x(1).toString
      val age = x(2).toInt
      Row(id, name, age) //封装一行数据
    })

    // 封装schema(描述DataFrame信息)sql-表
    val schema: StructType = StructType(List(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))

    // 创建DataFram
    val userDf: DataFrame = sparkSession.createDataFrame(rowRdd, schema)
    // 注册表
    userDf.createTempView("user_t")
    // 写SQL
    val usql: DataFrame = sparkSession.sql("select * from user_t order by age")
    // 查看结果 show dtabase;
    usql.show()
    // 释放资源
    sparkSession.stop()
  }

}
