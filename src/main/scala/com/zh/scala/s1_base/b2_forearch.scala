package com.zh.scala.s1_base

/**
  * for循环
  * 2019/3/3 23:24
  */
object b2_forearch {

  def main(args: Array[String]): Unit = {

  }

  /**
    * 1到20的数字，接收对象是Range, <- 表示用变量接收循环的Range的值
    */
  def f1(): Unit = {
    val rs = 1 to 20
    for (x <- rs)
      println(x)
  }

  /**
    * 定义数组，遍历数据的值
    */
  def f2(): Unit = {
    val arr = Array(1, 3, 5)
    for (x <- arr)
      println(x)
  }

  /**
    * 嵌套循环，加if判断筛选结果
    */
  def f3(): Unit = {
    for (i <- 1 to 10; j <- 1 to 5 if i != j)
      print((10 * i + j) + " ")
  }

  /**
    * yield是生成新的集合关键字，默认类型Vector
    */
  def f4(): Unit = {
    println(for (i <- 1 to 10) yield i * 10)
  }

  /**
    * to是.to()方法的简写形式
    * map 取出集合元素
    * _ 代表元素
    */
  def f5(): Unit = {
    println(1.to(10).map(_ * 100))
  }

  /**
    * 筛选符合条件的i值，能被2整除的数
   */
  def f6(): Unit = {
    val a1 = for (i <- 1 to 10; if (i % 2 == 0)) yield i
    println(a1)
  }



}

