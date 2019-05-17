package com.zh.spark.day2_count

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 解析日志，统计每个网页的访问次数
  */
object MyTomcatLogCount {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("MyTomCatLogCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    /*
     * 读入日志 解析：
     * 192.168.88.1 - - [30/Jul/2017:12:54:37 +0800] "GET /MyDemoWeb/oracle.jsp HTTP/1.1" 200 242
     */
    val rdd1: RDD[(String, Int)] = sc.textFile("H:\\tmp_files\\localhost_access_log.txt").map(line => {
      //解析字符串，得到jsp的名字
      //1、解析两个引号之间的字符串
      val index1: Int = line.indexOf("\"")
      val index2: Int = line.lastIndexOf("\"")
      val line1: String = line.substring(index1 + 1, index2) // GET /MyDemoWeb/oracle.jsp HTTP/1.1

      //得到两个空格的位置
      val index3: Int = line1.indexOf(" ")
      val index4: Int = line1.lastIndexOf(" ")
      val line2: String = line1.substring(index3 + 1, index4) // /MyDemoWeb/oracle.jsp

      //得到jsp的名字
      val jspName: String = line2.substring(line2.lastIndexOf("/")) // oracle.jsp

      (jspName, 1)
    })
    //统计出每个jsp的次数
    val rdd2: RDD[(String, Int)] = rdd1.reduceByKey(_ + _)

    //使用value排序,take取前2条
    val rdd3: RDD[(String, Int)] = rdd2.sortBy(_._2, false)
    rdd3.take(2).foreach(println)
    sc.stop()
    // flume  ---> hdfs ---->  计算
  }

}
