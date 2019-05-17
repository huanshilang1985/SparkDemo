package com.zh.scala.s3_class

/**
  * 类的构造器访问权限
  *
  * 主构造器定义成private，其他类是不能访问的
  * 2019/3/8 23:10
  */
class Person4 private(var name: String, age: Int) {

  var high: Int = _

  //被private修饰的辅助构造器
  private def this(name: String, age: Int, high: Int) {
    this(name, age)
    this.high = high
  }

}
