package com.zh.spark.day2_count

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  * 2019/4/2 9:45
  *
  * 需求：计算网页访问量前三名
  * 帮助企业做经营和决策
  */
object UrlCount {

  def main(args: Array[String]): Unit = {
    //文件路径
    val path: String = getClass.getClassLoader.getResource("itstar.log").getFile
    // 加载数据
    val conf: SparkConf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf);
    // 载入数据
    val rdd1: RDD[String] = sc.textFile(path)
    //对数据进行计算  w,1  h,1
    var rdd2 : RDD[(String, Int)] = rdd1.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1),1)  //标注为出现1次
    })
    // 将相同的网站进行累加求和， 网页，201
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_+_)

    rdd3.foreach(x => {
      println("网址为：" + x._1 + ", 访问量为：" + x._2)
    })
    sc.stop()
  }

}
