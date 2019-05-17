package com.zh.scala.s8_implicit

/**
  * 隐式参数
  * 2019/3/14 19:23
  */
object ImplicitTest {

  /**
    * 普通方法
    */
  def sleep(how:String): Unit ={
    println(how)
  }

  /**
    * 把参数定义为隐式，并设置的默认值
    */
  def sleep2(implicit how:String="还可以"): Unit ={
    println(how)
  }

  /**
    * 定义隐式参数，没有默认值
    * main方法中，会自动从上下文中找隐式参数
    */
  def sleep3(implicit how:String): Unit ={
    println(how)
  }

  def main(args: Array[String]): Unit = {
//    sleep("很香")
//    sleep2
    implicit val how = "头疼"
    sleep3
  }

}
