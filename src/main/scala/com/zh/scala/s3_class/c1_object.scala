package com.zh.scala.s3_class

/**
  * 在scala中Object是一个单例对象
  * Object定义的成员变量和方法都是静态的
  * 可以通过 类名. 来进行调用
  * 2019/3/7 22:10
  */
object c1_object {

  //定义成员变量
  val name:String = "zhanghe"

  //定义方法public static
  def sleep(str:String):Unit = {
    println(str + "在睡觉")
  }

}
