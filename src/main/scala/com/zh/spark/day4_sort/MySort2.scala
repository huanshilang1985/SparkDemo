package com.zh.spark.day4_sort

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @author zhanghe
  * 2019/4/3 10:41
  * 自定义顺序，模式匹配
  */
object MySort2 {

  def main(args: Array[String]): Unit = {
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("MySort1").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    // 创建数组
    val girl: Array[String] = Array("reba,18,80", "mimi,22,180", "liya,30,80", "jingtian,18,78")
    // 转换RDD
    val grdd1: RDD[String] = sc.parallelize(girl)

    // 切分数据
    val grdd2: RDD[(String, Int, Int)] = grdd1.map(line => {
      val fields: Array[String] = line.split(",")
      //获取每个属性
      val name:String = fields(0)
      val age:Int = fields(1).toInt
      val weight:Int = fields(2).toInt
      //元组输出
      (name, age, weight)
    })

    //5.模式匹配方式进行排序
    val sorted: RDD[(String, Int, Int)] = grdd2.sortBy(s => Girl1(s._1,s._2,s._3))
    val r:Array[(String, Int, Int)] = sorted.collect()
    println(r.toBuffer)
    sc.stop()
  }

}

case class Girl1(val name: String, val age: Int, val weight: Int) extends Ordered[Girl1] {

  override def compare(that: Girl1): Int = {
    //如果年龄相同 体重重的往前排
    if (this.age == that.age) {
      //如果正数 正序 负数 倒序
      -(this.weight - that.weight)
    } else {
      //年龄小的往前排
      this.age - that.age
    }
  }

  override def toString: String = s"名字:$name,年龄:$age,体重:$weight"
}


