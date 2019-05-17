package com.zh.scala.s1_base

/**
  * zhanghe
  * 方法的定义
  * 2019/3/4 19:23
  */
object b3_methods {

  def main(args: Array[String]): Unit = {

  }

  /**
    * 定义加法的方法
    */
  def m1(a:Int, b:Int):Int = {
    a + b
  }

  /**
    * 定义乘法的方法，没有括号
    */
  def m2(a:Int, b:Int):Int = a * b

  /**
    * 无参数，无返回值的方法
    */
  def callName = println("Hello T is waahah...")


}
