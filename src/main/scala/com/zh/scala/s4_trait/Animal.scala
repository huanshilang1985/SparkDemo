package com.zh.scala.s4_trait

/**
  * 2019/3/9 23:15
  * Trait 相当于Java里面的接口
  * 定义动物Trait
  */
trait Animal {

  /**
    * 定义未实现的方法
    */
  def eat(name: String)

  /**
    * 定义实现的方法
    */
  def sleep(name: String): Unit = {
    println(s"$name ->在睡觉")
  }

}
