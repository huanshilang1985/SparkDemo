package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 窗口操作：每10秒钟，把过去30秒的数据读取出来
  */
object yb3_MyNetworkWordcountByWindow {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyNetworkWordCount").setMaster("local[2]")
    //Seconds(1) 表示采样时间
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(2))

    // 创建DStream 从netcat服务器上接收
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.109.133", 1234, StorageLevel.MEMORY_ONLY)
    // 分词操作 给每个单词记一次数
    val words: DStream[(String, Int)] = lines.flatMap(_.split("")).map((_, 1))

    /**
      * reduceByKeyAndWindow 函数三个参数: 1、需要进行什么操作 2、窗口的大小30s 3、窗口滑动的距离10s
      * The slide duration of windowed DStream (10000 ms) must be a multiple of the slide duration of parent DStream (3000 ms)
      * 注意：窗口滑动距离必须是采样时间的整数倍
      */
    val result: DStream[(String, Int)] = words.reduceByKeyAndWindow((x:Int, y:Int) => (x + y), Seconds(30), Seconds(10))
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
