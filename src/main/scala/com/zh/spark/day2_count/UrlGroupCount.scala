package com.zh.spark.day2_count

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  * 2019/4/2 17:37
  *
  * 需求：求出每个学院访问第一个位的网址
  *   bigdata:aa
  *   java:bb
  *   python:cc
  */
object UrlGroupCount {

  def main(args: Array[String]): Unit = {
    // 文件路径
    val path: String = getClass.getClassLoader.getResource("itstar.log").getFile
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("UrlGroupCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    // 获取数据
    val rdd1: RDD[String] = sc.textFile(path)

    // 切分数据
    val rdd2: RDD[(String, Int)] = rdd1.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1), 1) //网址计数
    })

    // 求出总访问量  (网址，总访问量)
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_ + _);

    val rdd4: RDD[(String, String, Int)] = rdd3.map(f = x => {
      val url: String = x._1
      val host:String = new URL(url).getHost  //拿到host
      val xueyuan: String = host.split("[.]")(0) //拿到学院名，.在这里不能直接处理，要加[]
      (xueyuan, url, x._2) //元组输出
    })

    // 按照学院进行分组
    val rdd5: RDD[(String, List[(String, String, Int)])] = rdd4.groupBy(_._1).mapValues(it => {
      //倒序排序，sortBy默认正序排列，reverse翻转，take(1)取第一个
      it.toList.sortBy(_._3).reverse.take(1)
    })

    // 遍历打印
    rdd5.foreach(x => {
      println("学院为：" + x._1 + "，访问量第一的是：" + x._2)
    })
    // 关闭
    sc.stop()
  }

}
