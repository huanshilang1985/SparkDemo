package com.zh.spark.day5_sparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * @author zhanghe
  * 2019/4/4 14:38
  * SparkSQL，DSL风格
  */
object SqlTestDSL {

  def main(args: Array[String]): Unit = {
    //文件路径
    val path: String = getClass.getClassLoader.getResource("user.txt").getFile
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder()
      .appName("SqlTestDSL")
      .master("local[2]")
      .getOrCreate()
    // 创建RDD
    val rdd1: RDD[String] = sparkSession.sparkContext.textFile(path)
    // 切分数据
    val rdd2: RDD[Array[String]] = rdd1.map(_.split(" "))

    val rowRdd: RDD[Row] = rdd2.map(x => {
      val id = x(0).toInt
      val name = x(1).toString
      val age = x(2).toInt
      Row(id, name, age) //Row表示一行数据
    })

    //结构字段
    val schema: StructType = StructType(List(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))

    // Rdd创建为dataFrame
    val userDF: DataFrame = sparkSession.createDataFrame(rowRdd, schema)
    // DSL风格  rdd datatFrame dataSet
    import sparkSession.implicits._
    //查询年龄大于19
//    val user1Df: Dataset[Row] = userDF.where($"age" > 19)
    //加排序
    val user1Df: Dataset[Row] = userDF.where($"age" > 19).sort("age")

    //显示
    user1Df.show()
    // 关闭资源
    sparkSession.stop()

  }

}
