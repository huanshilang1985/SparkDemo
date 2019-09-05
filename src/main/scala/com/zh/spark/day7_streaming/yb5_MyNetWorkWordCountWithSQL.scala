package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 集成Spark SQL：在 Spark Streaming中使用SQL语句
  */
object yb5_MyNetWorkWordCountWithSQL {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyNetworkWordCount").setMaster("local[2]")
    //Seconds(3) 表示采样时间间隔
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    //创建DStream 从netcat服务器上接收数据
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.109.133", 1234, StorageLevel.MEMORY_ONLY)
    //进行单词计数
    val words: DStream[String] = lines.flatMap(_.split(" "))

    // 集成SparkSQL 使用SQL语句实现WordCount
    words.foreachRDD(rdd => {
      //创建一个Spark Session对象,  通过 ssc.sparkContext.getConf 直接获取此session的conf
      val spark:SparkSession = SparkSession.builder().config(ssc.sparkContext.getConf).getOrCreate()
      SparkSession.builder().config(ssc.sparkContext.getConf).getOrCreate()
      import spark.implicits._
      //注入隐式转换，把RDD转换成DataFrame, 表df1只有一列名字叫word
      val df1: DataFrame = rdd.toDF("word")
      // 创建视图
      df1.createOrReplaceTempView("words")
      //执行SQL 通过SQL实现wordcount
      spark.sql("select word,count(1) from words group by word").show()
    })
    ssc.start()
    ssc.awaitTermination()
  }

}
