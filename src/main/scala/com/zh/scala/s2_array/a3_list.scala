package com.zh.scala.s2_array

import scala.collection.mutable._
import scala.collection.immutable._

/**
  * 集合
  * 2019/3/6 21:25
  */
object a3_list {

  def main(args: Array[String]): Unit = {
    list_arrayBuffer
  }

  /**
    * 不可变集合 import scala.collection.immutable._
    * List是不可变集合，值和长度是不可变的。
    * 第二行代码会报错
    */
  def list_mutable: Unit = {
    var s = List[Int](1, 2, 3)
    //    s(0) = 12
  }

  /**
    * 可变集合import scala.collection.mutable._
    * ArrayBuffer是可变集合，数据可变，长度可变
    */
  def list_arrayBuffer: Unit = {
    var buff = ArrayBuffer(2, 3, 4)
    buff += 5
    buff(1) = 10
    println(buff.toBuffer)
  }

  /**
    * 直接写上引用包路径，声明集合
    */
  def other: Unit ={
    var list = scala.collection.mutable.ArrayBuffer(11,22,33)

  }

}
