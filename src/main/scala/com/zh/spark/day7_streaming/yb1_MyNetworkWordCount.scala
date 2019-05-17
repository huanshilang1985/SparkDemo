package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 基础-流式计算程序
  * 知识点：
  * 1、创建一个StreamingContext对象    ---->  核心：创建一个DStream
  * 2、DStream的表现形式：就是一个RDD
  * 3、使用DStream把连续的数据流变成不连续的RDD
  * spark Streaming 最核心的内容
  */
object yb1_MyNetworkWordCount {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyNetworkWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //创建DStream 从netcat服务器上接收数据
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.109.133", 1234, StorageLevel.MEMORY_ONLY)
    //lines中包含了netcat服务器发送过来的数据,分词操作
    val words: DStream[String] = lines.flatMap(_.split(" "))
    //计数， transform是把RDD转换成另一个RDD
    val wordPair: DStream[(String, Int)] = words.transform(x => x.map(x=>(x,1)))
    wordPair.print()
    ssc.start()   //启动StreamingContext 进行计算
    ssc.awaitTermination()  //等待任务结束
  }

}
