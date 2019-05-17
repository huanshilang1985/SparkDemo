package com.zh.spark.day5_sparkSql

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @author zhanghe
  *         2019/4/4 16:52
  *         SparkSQL DSL join
  */
object JoinDSL {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JoinDemo").master("local[2]").getOrCreate()

    import sparkSession.implicits._
    //直接创建DataSet
    val datas1: Dataset[String] = sparkSession.createDataset(List("1 hunter 18", "2 reba 22", "3 mimi 16"))
    //整理数据
    val dataDS1: Dataset[(Int, String, Int)] = datas1.map(x => {
      val fields: Array[String] = x.split(" ")
      val id = fields(0).toInt
      val name = fields(1).toString
      val age = fields(2).toInt
      (id, name, age) //元组输出
    })
    //转成DataDrame
    val dataDF1: DataFrame = dataDS1.toDF("id", "name", "age")

    //创建第二份数据
    val datas2: Dataset[String] = sparkSession.createDataset(List("18 young", "22 old"))

    val dataDS2: Dataset[(Int, String)] = datas2.map(x => {
      val fields: Array[String] = x.split(" ")
      val age = fields(0).toInt
      val desc = fields(1).toString
      (age, desc) //元祖输出
    })
    //3.转化为dataFrame
    val dataDF2: DataFrame = dataDS2.toDF("dage", "desc")

    val result: DataFrame = dataDF1.join(dataDF2,$"age" === $"dage")
//    val r = dataDF1.join(dataDF2,$"age" === $"dage","left")
//    val r = dataDF1.join(dataDF2,$"age" === $"dage","right")
//    val r = dataDF1.join(dataDF2,$"age" === $"dage","left_outer")
//    val r = dataDF1.join(dataDF2,$"age" === $"dage","cross")
    result.show()

    sparkSession.stop()
  }

}

//+---+------+---+----+-----+
//| id|  name|age|dage| desc|
//+---+------+---+----+-----+
//|  1|hunter| 18|  18|young|
//|  2|  reba| 22|  22|  old|
//+---+------+---+----+-----+