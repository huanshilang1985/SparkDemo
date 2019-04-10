package com.zh.spark.day2_count

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @author zhanghe
  * 2019/4/2 18:01
  *
  * 需求：加入自定义分区
  * 按照学院分区，相同的学院分为一个结果文件
  */
object UrlParCount {

  def main(args: Array[String]): Unit = {
    // 文件路径
    val path: String = getClass.getClassLoader.getResource("itstar.log").getFile
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("UrlParCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //拿到文件内容
    val rdd1: RDD[String] = sc.textFile(path)

    // 切分数据
    val rdd2: RDD[(String, Int)] = rdd1.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1),1)
    })

    // 聚合
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_+_)

    //自定义输出格式
    val rdd4: RDD[(String, (String, Int))] = rdd3.map(x => {
      val url:String = x._1
      val host:String = new URL(url).getHost
      val xueyuan:String = host.split("[.]")(0)
      (xueyuan,(url, x._2))
    })

    val xueyuan: Array[String] = rdd4.map(_._1).distinct().collect
    val xueYuanPartitioner: XueYuanPartitioner = new XueYuanPartitioner(xueyuan)

    // 加入分区规则
    val rdd5: RDD[(String, (String, Int))] = rdd4.partitionBy(xueYuanPartitioner).mapPartitions(it => {
      it.toList.sortBy(_._2._2).reverse.take(1).iterator
    })

    // 结果存储
    rdd5.saveAsTextFile("c://partout")
    sc.stop()
  }

}

class XueYuanPartitioner(xy:Array[String]) extends Partitioner {

  var rules:mutable.HashMap[String, Int] = new mutable.HashMap[String,Int]()
  var number = 0

  //遍历学院
  for(i <- xy){
    rules += (i -> number) //学院与分区号对应，存入Map
    number += 1  //分区号递增
  }

  //总的分区个数
  override def numPartitions: Int = xy.length

  //拿到的分区
  override def getPartition(key: Any): Int = {
    rules.getOrElse(key.toString, 0)  //如果没有就返回0
  }
}
