package com.zh.scala.s5_abstract

/**
  * 定义抽象类
  */
abstract class AbstractDemo {

  def eat(food:String)

  def sleep(how:String):Unit = {
    println(s"$how -> 很香")
  }
}
