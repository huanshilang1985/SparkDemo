package com.zh.scala.s1_base

/**
  * 运算符
  * 2019/3/7 11:50
  */
object b12_fuhao {

  def main(args: Array[String]): Unit = {
//    fuhao1
//    fuhao2
    fuhao3
  }

  /**
    * 算数运算符
    * + 加号
    * - 减号
    * * 乘号
    * / 除号
    * % 取余
    */
  def fuhao1: Unit = {
    val a = 10
    val b = 20
    val c = 25
    val d = 25
    println("算数运算符：")
    println("a + b = " + (a + b))
    println("a - b = " + (a - b))
    println("a * b = " + (a * b))
    println("b / a = " + (b / a))
    println("b % a = " + (b % a))
    println("c % a = " + (c % a))
  }

  /**
    * 关系运算符  ==  等于
    * !=  不等于
    * >   大于
    * <   小于
    * >=  大于等于
    * <=  小于等于
    */
  def fuhao2: Unit = {
    val a = 10
    val b = 20
    println("关系运算符")
    println("a == b = " + (a == b))
    println("a != b = " + (a != b))
    println("a > b = " + (a > b))
    println("a < b = " + (a < b))
    println("b >= a = " + (b >= a))
    println("b <= a = " + (b <= a))
  }

  /**
    * 逻辑运算符
    * && 逻辑与
    * || 逻辑或
    * !  逻辑非
    */
  def fuhao3: Unit = {
    val a = true
    val b = false
    println("a && b = " + (a && b))
    println("a || b = " + (a || b))
    println("!(a && b) = " + !(a && b))
  }

}
