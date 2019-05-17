package com.zh.scala.s1_base

/**
  * zhanghe
  * 传值调用、传名调用
  * 2019/3/4 19:45
  */
object b5_aliPay {

  def main(args: Array[String]): Unit = {
//    printMoney(5)    //传值调用

    printMoney2(balance())   //传递的是函数，将balance方法的名称传递到方法内部执行
  }

  var money = 1000

  /**
    * 吃一次花50元
    */
  def eat(): Unit = {
    money = money - 50
  }

  /**
    * 余额
    */
  def balance():Int = {
    eat()
    money
  }

  /**
    * 传值调用
    * @param x
    */
  def printMoney(x: Int):Unit = {
    for(a <- 1 to x) {
      balance();
      println("你的余额是：" + money)
    }
  }

  /**
    * 传名调用
    * 此时余额减了5次，x: => Int 表示的是一个方法的签名，没有参数
    * 返回值是int类型的函数
    * @param x
    */
  def printMoney2(x: => Int):Unit = {
    for(a <- 1 to 5) {
      println(f"你的余额是为：$x")
    }
  }

}
