package com.zh.scala.s2_array

/**
  * tuple 元组中可以是任意元素
  * 2019/3/7 21:18
  */
object s7_tuple {

  def main(args: Array[String]): Unit = {
//    tuple1
//    tuple2
    tuple3
  }


  /**
    * 普通元组
    */
  def tuple1: Unit = {
    val t = (2, true, "zhanghe", Unit)
    println(t._1)
    println(t._2)
  }

  /**
    * 对偶元组：有两个元素的元组
    */
  def tuple2: Unit = {
    val t1 = ("zhanghe", 18)
    println(t1.toString())
    val t2 = t1.swap //对偶元组交换位置
    println(t2.toString())
  }

  /**
    * 元组的折叠计算
    * 计算年龄的总和
    */
  def tuple3: Unit = {
    val t3 = Array(("zhanghe", 20), ("lisi", 19)) //存元组的数组
    var tmp = t3.foldLeft(1)(_ + _._2)  //第一个_代表初始值0, 第二个_代表元组，第三个_代表年龄
    println(tmp.toString)
  }

}
