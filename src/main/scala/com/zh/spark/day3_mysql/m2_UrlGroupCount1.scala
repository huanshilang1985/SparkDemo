package com.zh.spark.day3_mysql

import java.net.URL
import java.sql.{Connection, DriverManager}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  * 2019/4/2 21:54
  * 需求：把最终的结果存储在mysql中
  */
object m2_UrlGroupCount1 {

  def main(args: Array[String]): Unit = {
    //文件路径
    val path: String = getClass.getClassLoader.getResource("itstar.log").getFile
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("UrlGourpCount1").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    //获取文件内容
    val rdd1: RDD[String] = sc.textFile(path)
    // 切分数据
    val rdd2: RDD[(String, Int)] = rdd1.map(line => {
      val s: Array[String] = line.split("\t")
      (s(1), 1)
    })
    // 累加求和
    val rdd3: RDD[(String, Int)] = rdd2.reduceByKey(_+_)
    // 取出分组的学院
    val rdd4: RDD[(String, Int)] = rdd3.map(x => {
      val url: String = x._1
      val xueyuan: String = new URL(url).getHost.split("[.]")(1)
      (xueyuan, x._2)
    })

//    val rddCache: RDD[(String, Int)] = rdd4.cache()  //把结果放内存里，会更快

//    sc.setCheckpointDir("hdfs://****")
//    rdd4.checkpoint();    //放结果放在磁盘里，保证数据库安全

    //根据学院分组
    val rdd5: RDD[(String, List[(String, Int)])] = rdd4.groupBy(_._1).mapValues(it => {
      it.toList.sortBy(_._2).reverse.take(3)
    })

    rdd5.foreach(x => {
      val conn: Connection = DriverManager.getConnection("jdbc:mysql://10.1.255.101:3306/zhanghe?charatorEncoding=utf-8","root","root")
      val sql = "INSERT INTO url_data(xueyuan, number_one) values(?,?)"
      val statement = conn.prepareStatement(sql)
      statement.setString(1, x._1)
      statement.setString(2, x._2.toString())
      statement.executeUpdate()
      statement.close()
      conn.close()
    })
    sc.stop()
  }

}
