package com.zh.spark.day1_wc

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark实现WordCount的本地模式测试
  */
object SparkWordCount {

  def wd(args: Array[String]) :Unit= {
    // 2. 设置参数：setAppName程序名，setMaster本地测试设置线程数 *多个,local[2]是2个进程，结果是2个输出文件，结果内容会跟分区有关
    var conf : SparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[2]")

    // 1. 创建Spark执行程序的入口
    var sc : SparkContext = new SparkContext(conf)

    // 3. 加载数据，并且数据
    sc.textFile(args(0))
      .flatMap(_.split(" "))   //用/t切分数，并压平
      .map((_,1))            // 每个单词后面加数字1
      .reduceByKey(_+_)      // 聚合，累加求和
      .sortBy(_._2,false) //按次数倒序排列
      .saveAsTextFile(args(1)) //保存文件
    // 4.关闭资源
    sc.stop()
  }

  def main(args: Array[String]): Unit = {
//    var arr = Array[String]("D:/app/wc1.txt","D:/app/out")
    var arr = Array[String](args(0), args(1))
    wd(arr)
  }

}
