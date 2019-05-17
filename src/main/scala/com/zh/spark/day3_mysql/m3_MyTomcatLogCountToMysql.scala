package com.zh.spark.day3_mysql

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 把处理结果存到MySql数据库
  */
object m3_MyTomcatLogCountToMysql {

  def main(args: Array[String]): Unit = {
    //创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("MyTomcatLogCountToMysql").setMaster("local[2]")
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

    /*
     * 第一种修改方式，功能上可以实现，但每条数据都会创建连接，对数据库造成很大压力
     *
     * 针对分区来操作：一个分区，建立一个连接即可
     */
    rdd1.foreachPartition(saveToMysql)
    sc.stop()
  }


  def saveToMysql(it: Iterator[(String, Int)]) = {
    var conn: Connection = null
    var pst: PreparedStatement = null

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?serverTimezone=UTC&characterEncoding=utf-8", "root", "123456")
      pst = conn.prepareStatement("insert into mydata values(?,?)")

      it.foreach(f => {

        pst.setString(1, f._1)
        pst.setInt(2, f._2)

        pst.executeUpdate()
      })
    } catch {
      case t: Throwable => t.printStackTrace()
    } finally {
      if (pst != null) pst.close()
      if (conn != null) conn.close()
    }
  }

}
