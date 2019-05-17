package com.zh.scala.s9_fanxing

/**
  * 泛型的测试类
  * 2019/3/14 20:53
  */
object ScalaTest {

  def main(args: Array[String]): Unit = {
    val p = new Person[Int,Double, Double](18, 165, 100)
    println(p.age+ " "+ p.high + " " + p.face)
  }

}
