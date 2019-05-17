package com.zh.spark.day7_streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 使用检查点、updateStateByKey 实现累加操作
  */
object yb4_MyTotalNetworkWordCount {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //创建一个Streaming Context对象, local[2] 表示开启了两个线程, Seconds(3) 表示采样时间间
    val conf: SparkConf = new SparkConf().setAppName("yb_MyNetworkWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

    //设置检查点目录，保存之前的状态信息
    ssc.checkpoint("htfs://192.168.109.131:8020/tmp_files/chkp")

    //创建DStream，接收此IP端口消息
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.109.133", 1234, StorageLevel.MEMORY_ONLY)
    val words: DStream[String] = lines.flatMap(_.split(" ")) //切词
    val wordPair: DStream[(String, Int)] = words.map((_, 1)) //转换RDD格式

    /*
     * 定义一个值函数，进行累加运算
     * 接收两个参数：1、当前的值是多少  2、之前的结果是多少
     */
    val addFunc = (currentValues: Seq[Int], previousValues: Option[Int]) => {
      //把当前值的序列进行累加
      val currentTotal: Int = currentValues.sum
      //把之前的值上再累加
      Some(currentTotal + previousValues.getOrElse(0))
    }
    //进行累加运算
    val total: DStream[(String, Int)] = wordPair.updateStateByKey(addFunc)
    total.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
