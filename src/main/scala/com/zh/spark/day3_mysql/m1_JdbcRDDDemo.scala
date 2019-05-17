package com.zh.spark.day3_mysql

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  *  2019/4/2 23:07
  *
  *  spark提供的连接mysql的方式jdbcRDD
  */
object m1_JdbcRDDDemo {

  //匿名函数
  val connection = () => {
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection("jdbc:mysql://10.1.255.101:3306/zhanghe?charatorEncoding=utf-8","root","root")
  }

  def main(args: Array[String]): Unit = {
//    System.setProperty("hadoop.home.dir", "G:\\bin\\hadoop-2.5.2")
    val conf: SparkConf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //执行SQL
    val jdbcRdd: JdbcRDD[(Int, String, String)] = new JdbcRDD(
      sc, connection, "select * from url_data where id >= ? and id <= ?", 1000, 2000, 1,
      r => {
        //定义返回格式
        val id:Int = r.getInt(1)
        val xueyuan:String = r.getString(2)
        val number_one:String = r.getString(3)
        (id, xueyuan, number_one)
      }
    )
    val jrdd: Array[(Int, String, String)] = jdbcRdd.collect()
    println(jrdd.toBuffer)
    sc.stop()

  }

}
