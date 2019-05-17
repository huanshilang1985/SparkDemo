package com.zh.scala.s3_class

/**
  * @Author zhanghe
  * @Desc:
  * @Date 2019/3/9 23:05
  */
class Person6(private var name: String, age: Int) {

  var high: Int = _

  def this(name: String, age: Int, high: Int) {
    this(name, age)
    this.high = high
  }

}

/**
  * 伴生对象：单例类名与类名相同
  * 在伴生对象里可以访问类的私有成员方法和属性
  */
object Person6 extends App {
  var p = new Person6("zhanghe", 18)
  println(p.name)
}