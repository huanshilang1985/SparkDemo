package com.zh.scala.s4_trait

/**
  * 2019/3/9 23:18
  * 继承了特质的实现类
  * extends继承特质的关键字
  * with同时继承其他的特质的关键字
  * 混入特质：可以同时继承多个特质
  */
class Pig extends Animal with Run {

  /**
    * 定义未实现的方法
    */
  override def eat(name: String): Unit = {
    println(s"$name -> 吃食物")
  }

  /**
    * 定义实现的方法
    * 重写了已实现的方法
    */
  override def sleep(name: String): Unit = {
    println(s"$name -> 睡觉长膘")
  }

  override def howRun(name: String): Unit = super.howRun(name)

}

object Pig extends App {
  var pig = new Pig()
  pig.eat("猪")
  pig.sleep("猪")
  pig.howRun("猪")
}
