package com.zh.spark.day5_sparkSql

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * @author zhanghe
  *         2019/4/4 14:59
  *
  *         SparkSQL sql形式 实现WordCount
  */
object SqlWordCount {

  def main(args: Array[String]): Unit = {
    val path: String = getClass.getClassLoader.getResource("wc.txt").getFile

    val sparkSession: SparkSession = SparkSession.builder()
      .appName("SqlWordCount")
      .master("local[2]")
      .getOrCreate()

    //加载数据，使用dataSet处理数据，dataSet是一个更加智能的RDD，默认有一列叫value，value存储的是所有数据
    val datas: Dataset[String] = sparkSession.read.textFile(path)

    //sparkSql 注册表/注册视图 rdd.flatMap
    import sparkSession.implicits._
    val word: Dataset[String] = datas.flatMap(_.split(" "))
    // 注册视图
    word.createTempView("wc_t")

    val sql:String = "select value as word, count(*) sum from wc_t group by value order by sum desc";
    val sqlFrame: DataFrame = sparkSession.sql(sql)
    sqlFrame.show()

    sparkSession.stop()
  }

}

//+-------+---+
//|   word|sum|
//+-------+---+
//|  hello|  3|
//|zhanghe|  2|
//|    Tom|  1|
//|      I|  1|
//|  world|  1|
//|     am|  1|
//+-------+---+