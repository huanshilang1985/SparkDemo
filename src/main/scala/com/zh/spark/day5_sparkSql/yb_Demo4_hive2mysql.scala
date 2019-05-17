package com.zh.spark.day5_sparkSql

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 从Hive查询结果，存到MySQL里
  */
object yb_Demo4_hive2mysql {

  def main(args: Array[String]): Unit = {
    //创建SparkSession
    val spark:SparkSession = SparkSession.builder().appName("Demo4").enableHiveSupport().getOrCreate()

    //执行SQL
    val result:DataFrame = spark.sql("select deptno,count(1) from company.emp group by deptno")

    //将结果保存到mysql中
    val props = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "123456")

    result.write.mode("append").jdbc("jdbc:mysql://192.168.109.1:3306/company?serverTimezone=UTC&characterEncoding=utf-8", "emp_stat", props)

    spark.stop()
  }

}
