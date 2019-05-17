package com.zh.scala.s1_base

/**
  * zhanghe
  * 函数
  * 2019/3/4 19:35
  */
object b4_functions {

  def main(args: Array[String]): Unit = {
    println(h1(1,2))
  }

  /**
    * 定义函数方法1 ： 方法名 + 空格 + _
    * 定义函数方法2 : 不定义返回值，用 => 指定方法体
    */
  val h1 = (a:Int,b:Int) => {a * b}

}
