package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.flume.{FlumeUtils, SparkFlumeEvent}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark接收Flume的push消息
  */
object yb3_MyFlumeStream {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyFlumeStream").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //对接flume，创建一个flumeevent 从 flume中接收push来的数据   也是一个DStream
    //flume将数据push到"192.168.109.1", 1234  spark Streaming在这里监听
    val flumeEventDStream: ReceiverInputDStream[SparkFlumeEvent] = FlumeUtils.createStream(ssc, "192.168.109.1", 1234)

    val lineDStream: DStream[String] = flumeEventDStream.map(e => {
      new String(e.event.getBody.array())
    })
    lineDStream.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
