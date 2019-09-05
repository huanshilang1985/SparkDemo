package com.zh.spark.day7_streaming

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.storage.StorageLevel
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.FlumeUtils

/*
 * 测试 pull方式 使用 spark sink
 * Spark主动去flume取数据
 * flume配置文件：option5
 */
object yb8_FlumeLogPull {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    //创建一个Streaming Context对象
    //local[2] 表示开启了两个线程
    val conf: SparkConf = new SparkConf().setAppName("FlumeLogPull").setMaster("local[2]")
    //Seconds(3) 表示采样时间间隔
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //创建FlumeEvent的DStream，采用pull的方式
    val flumeEvent = FlumeUtils.createPollingStream(ssc, "192.168.109.133", 1234, StorageLevel.MEMORY_ONLY)

    //将FlumeEvent的事件准换成字符串
    val lineDStream: DStream[String] = flumeEvent.map(e => {
      new String(e.event.getBody.array)
    })

    //输出结果
    lineDStream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}