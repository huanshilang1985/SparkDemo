package com.zh.scala.s3_class

/**
  * 2019/3/8 23:21
  * private[this] 表示本包中，子包下可以访问
  * private[s3_class] 表示只有这个指定包路径下的类可以访问
  */
private[this] class Person5(var name: String, age: Int) {

  var high: Int = _

  def this(name: String, age: Int, high: Int) {
    this(name, age)
    this.high = high
  }
}
