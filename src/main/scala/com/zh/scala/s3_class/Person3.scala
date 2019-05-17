package com.zh.scala.s3_class

/**
  * 定义辅助构造器
  * 2019/3/8 22:13
  */
class c5_Person3(var name: String, var age: Int) {

  var high: Int = _
  var weight: Int = _

  /**
    * 定义辅助构造器
    * 辅助构造器可以是多个
    * 在辅助构造器中必须先调用主构造器
    */
  def this(name: String, age: Int, high: Int) {
    this(name, age)
    this.high = high
  }

  /**
    * 定义辅助构造器
    * 辅助构造器可以是多个
    */
  def this(name: String, age: Int, high: Int, weight: Int) {
    this(name, age)
    this.high = high
    this.weight = weight
  }

  override def toString: String = name + " " + age + " " + high + " " + weight

}

/**
  * 单例对象
  * 如果主构造器中成员变量属性没有被val var修饰的话，该属性不能被访问
  * 相当于java中没有提供get方法
  *
  * 如果成员属性被var修饰的话，相当于java中对外提供了get方式和set方式
  * 如果成员属性被val修饰的话，相当于java中对外只提供了get方式
  */
object person3_Test extends App {
  val p = new c5_Person3("张鹤", 30, 179, 140)
  println(p.toString)
  println(p.name)
}
