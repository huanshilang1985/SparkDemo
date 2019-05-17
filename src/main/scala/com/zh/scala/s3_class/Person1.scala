package com.zh.scala.s3_class

/**
  * 定义类
  * 2019/3/7 22:29
  */
class c1_Person1 {

  var name: String = _ //姓名, _ 表示未赋值

  var age: Int = _ //年龄

}

/**
  * 定义单例类
  */
object person_Test extends App {
  val p = new c1_Person1
  p.name = "zhanghe"
  p.age = 18

  println(p.name + " " + p.age)

}


