package com.zh.spark.day4_sort

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author zhanghe
  * 2019/4/3 9:48
  *
  * 实现自定义的排序，按照年龄进行排序
  */
object MySort1 {

  def main(args: Array[String]): Unit = {
    // 创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("MySort1").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    // 创建数组
    val girl: Array[String] = Array("reba,18,80","mimi,22,180","liya,30,80","jingtian,18,78")
    // 转换RDD
    val grdd1: RDD[String] = sc.parallelize(girl)

    // 切分数据
    val grdd2: RDD[Girl] = grdd1.map(line => {
      val fields: Array[String] = line.split(",")
      //获取每个属性
      val name:String = fields(0)
      val age:Int = fields(1).toInt
      val weight:Int = fields(2).toInt
      //元组输出
      new Girl(name, age, weight)
    })

    //因前面排序过，现在保持原来的顺序
    val sorted: RDD[Girl] = grdd2.sortBy(s => s)
    val r:Array[Girl]= sorted.collect()
    println(r.toBuffer)
    sc.stop()
  }

}

/**
  * 自定义类  Ordered
  * @param name   姓名
  * @param age    年龄
  * @param weight 体重
  */
class Girl(val name:String, val age:Int, val weight:Int) extends Ordered[Girl] with Serializable {
  override def compare(that: Girl): Int = {
    //如果年龄相同，体重大的往前排
    if(this.age == that.age) {
      //正数-正序排列，负数倒序排列
      -(this.weight - that.weight)
    } else {
      //年龄小的往前排
      this.age - that.age
    }
  }

  override def toString: String = s"名字:$name, 年龄:$age, 体重:$weight"
}
