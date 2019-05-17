package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 测试文件流，监控文件夹目录，如果有新文件产生，就读取出来
  */
object yb2_FileStreaming {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建StreamContext对象，local[2]表示启动2个线程，Seconds(3)表示采样时间间隔
    val conf: SparkConf = new SparkConf().setAppName("yb_FileStreaming").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    // 直接监控某个目录，如果有新文件产生，就读取进来
    val lines: DStream[String] = ssc.textFileStream("H:\\tmp_files\\test_file_stream")

    lines.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
