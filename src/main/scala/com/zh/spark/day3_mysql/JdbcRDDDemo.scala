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
object JdbcRDDDemo {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //匿名函数
    val connection = () => {
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection("jdbc:mysql://10.1.255.101:3306/zhanghe?charatorEncoding=utf-8","root","root")
    }

    val jdbcRdd: JdbcRDD[(Int, String, String)] = new JdbcRDD(
      sc,
      connection,
      "select * from url_data where id >= ? and id <= ?",
      1000, 2000, 1,
      r => {
        val id = r.getInt(1)
        val xueyuan = r.getString(2)
        val number_one = r.getString(3)
        (id, xueyuan, number_one)
      }
    )
    val jrdd = jdbcRdd.collect()
    println(jrdd.toBuffer)
    sc.stop()

  }

}
