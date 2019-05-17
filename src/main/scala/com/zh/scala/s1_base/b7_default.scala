package com.zh.scala.s1_base

/**
  * @author zhanghe
  * 方法默认值
  * 2019/3/4 20:19
  */
object b7_default {


  def main(args: Array[String]): Unit = {
    println(sum())
  }

  /**
    * 给函数加默认值
    * @param a
    * @param b
    * @return
    */
  def sum(a:Int = 3, b:Int = 7): Int ={
    a + b
  }
}
