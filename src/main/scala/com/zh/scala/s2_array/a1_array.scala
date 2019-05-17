package com.zh.scala.s2_array

/**
  * 数组的定义
  * 数组的长度不可变，val定义数组的值也是可变的
  * 2019/3/6 17:36
  */
object a1_array {

  def main(args: Array[String]): Unit = {
    //数组定义1：
    var arr: Array[String] = new Array[String](3)
    arr(0) = "wo"
    arr(1) = "shi"
    arr(2) = "shei"

    //数组定义2：
    val arr1: Array[Int] = Array[Int](1, 2, 3, 4, 5)
    arr1(1) = 18 //改变数组的值
    println(arr1(1))
  }

}
