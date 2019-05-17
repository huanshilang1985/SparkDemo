package com.zh.spark.day5_sparkSql

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @author zhanghe
  *         2019/4/4 16:28
  *
  *         SQL方式，两个表join关联
  */
object JoinSQL {

  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("JoinSQL").master("local[2]").getOrCreate()

    //直接创建DataSet
    import sparkSession.implicits._
    val datas1: Dataset[String] = sparkSession.createDataset(List("1 hunter 18", "2 reba 22", "3 mimi 16"))
    //整理数据
    val dataDS1: Dataset[(Int, String, Int)] = datas1.map(x => {
      val fields: Array[String] = x.split(" ")
      val id: Int = fields(0).toInt
      val name: String = fields(1).toString
      val age: Int = fields(2).toInt
      (id, name, age) //元组输出
    })
    //转成DataDrame
    val dataDF1: DataFrame = dataDS1.toDF("id", "name", "age")

    //创建第二份数据
    val datas2: Dataset[String] = sparkSession.createDataset(List("18 young", "22 old"))

    val dataDS2: Dataset[(Int, String)] = datas2.map(x => {
      val fields: Array[String] = x.split(" ")
      val age: Int = fields(0).toInt
      val desc: String = fields(1).toString
      (age, desc) //元祖输出
    })
    //3.转化为dataFrame
    val dataDF2: DataFrame = dataDS2.toDF("dage", "desc")

    //注册视图
    dataDF1.createTempView("d1_t")
    dataDF2.createTempView("d2_t")

    // 写SQL
    val result: DataFrame = sparkSession.sql("select name, desc from d1_t join d2_t on age = dage")
    //触发任务
    result.show()

    //关闭资源
    sparkSession.close()
  }

}

//+------+-----+
//|  name| desc|
//+------+-----+
//|hunter|young|
//|  reba|  old|
//+------+-----+