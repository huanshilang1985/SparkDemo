package com.zh.spark.day7_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}

/**
  * @author zhanghe
  * 2019/4/8 16:42
  *
  * 启动Zookeeper、Kafka发送消息
  * 本程序接口断点
  * Kafka使用receiver接收
  */
object KafkaWordCount {

  def main(args: Array[String]): Unit = {
    // 创建StreamingContext
    val conf: SparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Milliseconds(2000))

    // 接入Kafka数据源(如何访问Kafka集群？zookeeper)
    var zkQuorum: String = "10.1.255.101:2181,10.1.255.102:2181,10.1.255.103:2181" //zookeeper集群地址，2181是zk默认端口
    val groupId: String = "g1"   //访问组
    val topic: Map[String, Int] = Map("wc" -> 1)   //访问主题，1表示本地模式的1个线程
    //创建DStream(String, String)表示kafka写入的key和内容
    val kafkaStream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, zkQuorum, groupId, topic)

    // 处理数据(取出kafka里面的数据内容)
    val datas: DStream[String] = kafkaStream.map(_._2)

    // 启动Streaming程序
    val r: DStream[(String, Int)] = datas.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    r.print()
    ssc.start()

    // 关闭资源
    ssc.awaitTermination()
  }

}
