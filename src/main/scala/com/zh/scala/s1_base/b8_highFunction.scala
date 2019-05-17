package com.zh.scala.s1_base

/**
  * @author zhanghe
  * 高阶函数：将其他函数作为参数，或者结果是函数的函数
  * 2019/3/4 20:20
  */
object b8_highFunction {

  def main(args: Array[String]): Unit = {
    println(getPerson(Person, 12))
  }

  /**
    * 将其他函数作为参数，或者其结果是函数的函数
    *
    * @param h
    * @param f
    */
  def getPerson(h: Int => String, f: Int): String = {
    //函数h，参数f
    h(f)
  }

  def Person(x: Int):String = "我是" + x.toString + "岁, 厉害呀"

}
