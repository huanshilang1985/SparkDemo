//package com.zh.spark.day7_streaming
//
//
//import kafka.common.TopicAndPartition
//import kafka.message.MessageAndMetadata
//import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
//import org.I0Itec.zkclient.ZkClient
//import org.apache.commons.codec.StringDecoder
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.dstream.{DStream, InputDStream}
//import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
//import org.apache.spark.streaming.{Duration, StreamingContext}
//
///**
//  * @author zhanghe
//  *         2019/4/8 23:24
//  *         Kafka直连方式，与SparkStreaming整合
//  *
//  *         Kafka正在启动，对于Kafka消费数据进行处理。
//  *         如果程序终止，Kafka依然在运行，并且有数据，再次启动程序后，数据还能读取到
//  */
//object KafkaDirectWordCount {
//
//  def main(args: Array[String]): Unit = {
//    //1. 创建SparkStreaming程序入口
//    val conf: SparkConf = new SparkConf().setAppName("KafkaDirectWordCount").setMaster("local[2]")
//    val ssc: StreamingContext = new StreamingContext(conf, Duration(500))
//    //2. 定义变量
//    val group: String = "g1" //创建消费者组
//    val topic: String = "kdwc" //创建主题
//    val brokerList: String = "10.1.255.101:9092,10.1.255.102:9092,10.1.255.102:9092" //指定Kafka的Broker地址
//    val zkQuorum: String = "10.1.255.101:2181,10.1.255.102:2181,10.1.255.102:2181" // 指定Zookeeper地址，更新偏移量记录使用的zk：1存储、2监听
//    val topics: Set[String] = Set(topic) //创建Stream时使用的topic集合
//
//    //3. 创建zookeeper目录（存储偏移量）
//    val topicDirs: ZKGroupTopicDirs = new ZKGroupTopicDirs(group, topic)
//    //4. 获取Zookeeper存储偏移量的路径   /consumer/g1/wcd.../offsets /topic
//    val zkTopicPath: String = s"${topicDirs.consumerOffsetDir}"
//    //5. 设置好Kafka参数
//    val kafkaParams: Map[String, String] = Map(
//      "metadata.broker.list" -> brokerList, //设置broker地址
//      "group.id" -> group, //设置组ID
//      "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString //设置偏移量，从头读Smallest
//    )
//    //6. 设置zk客户端，用户更新偏移量
//    val zkClient: ZkClient = new ZkClient(zkQuorum)
//    //7. 如果有记录则说明以前有记录过偏移量  /consumer/g1/topics/
//    val children: Int = zkClient.countChildren(zkTopicPath)
//
//    //8. 创建KafkaStream 存在两种情况：1.第一次读 2.从当期偏移量读
//    var kafkaStream: InputDStream[(String, String)] = null
//    //9. 如果zk保存offset，我们利用此offset作为kafkaStream的其实位置
//    var fromOffsets: Map[TopicAndPartition, Long] = Map()
//
//    //10. 判断，如果保存过offset
//    if (children > 0) {
//      for (i <- 0 until children) {
//        val partitionOffset: String = zkClient.readData[String](s"$zkTopicPath/${i}")
//        val tp: TopicAndPartition = TopicAndPartition(topic, i)
//        fromOffsets += (tp -> partitionOffset.toLong)
//      }
//
//      //key: kafka的key，  value："具体的内容"
//      // 定义如何读取数据
//      val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.key(), mmd.message())
//      // 11. 创建KafkaUtils String String 表示的是DStream装的数据类型，key与value的解码器
//      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messageHandler)
//    } else {
//      // 如果没有保存偏移量
//      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
//    }
//    //12. 定义偏移量范围
//    var offsetRanges: Array[OffsetRange] = Array[OffsetRange]()
//
//    //13. 从Kafka读取消息 DStream
//    val transform: DStream[(String, String)] = kafkaStream.transform(rdd => {
//      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      rdd
//    })
//
//    //14. 拿到数据
//    val messages: DStream[String] = transform.map(_._2)
//
//    //15.一次迭代DSteam的Rdd进行计算
//    messages.foreachRDD { rdd =>
//      rdd.foreachPartition(partition =>
//        partition.foreach(x =>
//          println(x)
//        )
//      )
//    }
//
//    //16. 把分区对应的偏移量写到zookeeper中
//    for (o <- offsetRanges) {
//      // 拿到分区好
//      val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
//      // 分区的offset保存到zk
//      ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
//    }
//
//    //17.启动SparkStreaming
//    ssc.start()
//    //18. 退出
//    ssc.awaitTermination()
//  }
//
//}
