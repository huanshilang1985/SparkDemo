package com.zh.scala.s1_base

/**
  * @author zhanghe
  * 柯理化 : 把原本两个函数的参数，变成两个接收一个参数的函数
  * 2019/3/4 20:36
  */
object b10_kelihua {

  /**
    * 正常函数
    */
  def m1(a:Int, b:Int):Int = {
    a + b
  }

  /**
    * 柯理化后的函数
    */
  def m2(a:Int)(b:Int):Int = {
    a + b
  }

}
