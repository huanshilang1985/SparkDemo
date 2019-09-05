package com.zh.spark.day2_count

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * 自定义分区规则
  */
object MyTomcatLogPartitioner {

  def main(args: Array[String]): Unit = {
    //    System.setProperty("hadoop.home.dir", "G:\\bin\\hadoop-2.5.2")
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("MyTomcatLogPartitioner")
    val sc: SparkContext = new SparkContext(conf)

    /**
      * 读入日志 解析：
      * 192.168.88.1 - - [30/Jul/2017:12:54:37 +0800] "GET /MyDemoWeb/oracle.jsp HTTP/1.1" 200 242
      */
    val rdd1: RDD[(String, String)] = sc.textFile("H:\\tmp_files\\localhost_access_log.txt").map(line => {
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
      (jspName, line)
    })

    //定义分区规则 MyWebPartioner
    //得到不重复的jsp名字
    val rdd2: Array[String] = rdd1.map(_._1).distinct().collect()

    // 创建分区规则
    val myPartitioner: MyWebPartioner = new MyWebPartioner(rdd2)
    val rdd3: RDD[(String, String)] = rdd1.partitionBy(myPartitioner)

    //将rdd3 输出
    rdd3.saveAsTextFile("H:\\tmp_files\\test_partition")
    sc.stop()
  }

}

/**
  * 自定义分区规则类：用jsp名字做分区
  *
  * @param jspList
  */
class MyWebPartioner(jspList: Array[String]) extends Partitioner {
  //定义一个集合来保存分区条件   String 代表jsp的名字 Int 代表序号
  private val partitionMap: mutable.HashMap[String, Int] = new mutable.HashMap[String, Int]()
  var partID: Int = 0 // 初始分区号
  for (jsp <- jspList) {
    partitionMap.put(jsp, partID) //记录分区关系
    partID += 1
  }

  //返回有多少个分区
  def numPartitions: Int = partitionMap.size

  //根据jsp，返回对应的分区
  def getPartition(key: Any): Int = partitionMap.getOrElse(key.toString, 0)
}