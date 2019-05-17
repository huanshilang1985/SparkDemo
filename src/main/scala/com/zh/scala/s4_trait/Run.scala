package com.zh.scala.s4_trait

/**
  * 2019/3/9 23:24
  * 定义跑的特质
  */
trait Run {

  def howRun(name:String):Unit = {
    println(s"$name 在奔跑")
  }
}