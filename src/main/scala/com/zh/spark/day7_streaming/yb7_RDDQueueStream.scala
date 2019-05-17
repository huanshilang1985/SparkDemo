package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * RDD队列流
  */
object yb7_RDDQueueStream {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyNetworkWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //需要先创建一个队列 RDD[In
    val rddQueue: mutable.Queue[RDD[Int]] = new mutable.Queue[RDD[Int]]()
    //往队列里面添加数据  --->  创建数据源
    for(i <- 1 to 3) {
      rddQueue += ssc.sparkContext.makeRDD(1 to 10)
      //便于观察
      Thread.sleep(1000)
    }
    //从队列中接收数据，创建DStr
    val inputDStream: InputDStream[Int] = ssc.queueStream(rddQueue)
    val result: DStream[(Int, Int)] = inputDStream.map(x => (x, x*2))
    result.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
