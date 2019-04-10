package com.zh.spark.day4_sort

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}



/**
  * 自定义隐式排序的类
  */
object ImplicitRules {

  implicit object OrderingGirl extends Ordering[Girl3]{
    override def compare(x: Girl3, y: Girl3): Int = {
      if(x.age == y.age){
        -(x.weight - y.weight)  // 体重大的往前排
      } else {
        x.age - y.age  // 年龄小的往前排
      }
    }
  }
}

case class Girl3(val name: String, val age: Int, val weight: Int)

/**
  * @author zhanghe
  * 2019/4/3 10:50
  *
  * 自定义排序-隐式转换
  * 推荐使用定义一个隐式类，实现这些转换
  */
object MySort3 {

  def main(args: Array[String]): Unit = {
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("MySort3").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    // 创建数据
    val girl:Array[String] = Array("reba,18,80","mimi,22,100","liya,30,80","jingtian,18,78")
    // 转换RDD
    val grdd1: RDD[String] = sc.parallelize(girl)

    val grdd2: RDD[(String, Int, Int)] = grdd1.map(line => {
      val fields: Array[String] = line.split(",")
      //拿到每个属性
      val name = fields(0)
      val age = fields(1).toInt
      val weight = fields(2).toInt
      (name, age, weight)
    })

    import ImplicitRules.OrderingGirl
    val sorted: RDD[(String, Int, Int)] = grdd2.sortBy(s => Girl3(s._1, s._2, s._3))
    var result:Array[(String, Int, Int)] = sorted.collect()
    println(result.toBuffer)
  }
}
