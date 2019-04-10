package com.zh.spark.day7_streaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  * 2019/4/8 9:54
  *
  * rdd：创建的程序入口  sparkContext
  * dataFrame：创建的程序入口  sparkSession
  * Streaming是把数据切分成批次，在交给Spark处理
  */
object SteamingWordCount {

  def main(args: Array[String]): Unit = {
    // 创建SparkContrext
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    // 创建StreamingContext，参数：批次时间
    val ssc: StreamingContext = new StreamingContext(sc, Milliseconds(2000))

    //可以创建DStream，首先接入数据源，socket
    val datas: ReceiverInputDStream[String] = ssc.socketTextStream("10.1.255.101",7788)

    val rd: DStream[(String, Int)] = datas.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    //打印结果
    rd.print()
    // 需要启动SparkStreaming程序 exit quit
    ssc.start()
    ssc.awaitTermination()
  }
}
