package com.zh.spark.mianshiti

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 面试：统计文件内第四列的字母出现次数
  * 2019/4/25 15:43
  */
object WordCount4 {

  def main(args: Array[String]): Unit = {
    // 文件路径
    val path: String = getClass.getClassLoader.getResource("log.txt").getFile

    val conf: SparkConf = new SparkConf().setAppName("WordCount4").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    val wordRdd: RDD[(String, Int)] = sc.textFile(path).map(line => {
      val words: Array[String] = line.split(",")
      (words(3), 1)
    })
    val count: RDD[(String, Int)] = wordRdd.reduceByKey(_ + _)
//    count.foreach(w => println(w))
    count.saveAsTextFile(args(1))
    sc.stop()
  }

}
