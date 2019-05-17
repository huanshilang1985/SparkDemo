package com.zh.scala.s8_implicit

/**
  * 隐式参数
  * 2019/3/14 19:34
  */
object KelihuaImplicit {

  /**
    * 柯理化的方法（把参数分隔开）
    */
  def sum(a:Int)(b:Int):Int ={a+b}

  /**
    * 柯理化的方法，隐式参数
    * 隐式参数和隐式值要配对使用
    */
  def sum2(a:Int)(implicit b:Int):Int ={a+b}

  def main(args: Array[String]): Unit = {
//    println(sum(1)(5))

    implicit val b = 9  //隐式值
    println(sum2(1))


  }

}
