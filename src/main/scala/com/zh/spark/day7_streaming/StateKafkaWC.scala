package com.zh.spark.day7_streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}

/**
  * @author zhanghe
  * 2019/4/8 17:24
  * 记录Kafka历史状态的
  */
object StateKafkaWC {

  /**
    * 自定义函数：保留历史状态wc单词
    * String  表示单词
    * Seq[Int]  在每一个分区中出现的次数
    * Option[Int]  累加的结果
    */
  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) =>{
    /**
      * 总的次数=当前出现的次数+以前返回的结果
      * t._2.sum 当前出现的次数总和（因为是多个分区，所以要sum）
      * t._3.getOrElse(0)  累加的结果，有就取值，没有默认0
      */
    iter.map(t => (t._1, t._2.sum + t._3.getOrElse(0)))
  }

  def main(args: Array[String]): Unit = {
    // 创建程序入口
    val conf: SparkConf = new SparkConf().setAppName("StateKafkaWC").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Milliseconds(2000))
    ssc.checkpoint("hdfs://10.1.255.101:9000/spark/checkpoint")

    // 接入Kafka数据源(如何访问Kafka集群？zookeeper)
    var zkQuorum: String = "10.1.255.101:2181,10.1.255.102:2181,10.1.255.103:2181" //zookeeper集群地址，2181是zk默认端口
    val groupId: String = "g1"   //访问组
    val topic: Map[String, Int] = Map("wc" -> 1) //访问主题，1表示本地模式的1个线程
    //创建DStream(String, String)表示kafka写入的key和内容
    val kafkaStream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, zkQuorum, groupId, topic)

    // 处理数据
    val datas: DStream[String] = kafkaStream.map(_._2)

    /**
      * 加入历史数据
      * 参数1：自定义的业务函数
      * 参数2：分区器设置，当前使用的是默认分区
      * 参数3：是否使用
      **/
    val r: DStream[(String, Int)] = datas.flatMap(_.split(" ")).map((_, 1))
      .updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)

    // 打印
    r.print()
    // 启动程序
    ssc.start()
    // 关闭资源
    ssc.awaitTermination()
  }


}
