package com.zh.scala.s8_implicit

/**
  * 隐式
  * 2019/3/14 19:16
  */
object doubleToInt {

  /**
    * 定义一个隐式函数，不需要声明式使用，Scala会自动使用的
    */
  implicit def doubleToInt(d:Double):Int = d.toInt

  def main(args: Array[String]): Unit = {
    //double会自动转换为int类型
    val a:Int = 8.8
    println(a)

    /**
      * 查看已定义的隐式函数
      * :implicit -v
      */

  }

}
