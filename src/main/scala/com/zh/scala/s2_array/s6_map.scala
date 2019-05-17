package com.zh.scala.s2_array

import scala.collection.mutable

/**
  * map
  * 2019/3/7 21:05
  */
object s6_map {

  def main(args: Array[String]): Unit = {
    map2
  }

  /**
    * Map 不可变
    */
  def map1: Unit = {
    val map = Map[String, Int]("hello" -> 2, "rebba" -> 8)
  }

  /**
    * HashMap 可变
    */
  def map2: Unit = {
    val map = collection.mutable.HashMap[String, Int]()
    println("添加元素："+ map.put("hello", 10)) //添加元素
    println("添加元素："+ (map += "weit" -> 12))  //添加元素
    map.get("hello")
    println("获取元素：" + map.toBuffer)
    map.remove("hello")
    println("删除元素：" + map.toBuffer)
    map -= "weit"
    println("删除元素：" + map.toBuffer)
  }

  /**
    * getOrElse(key,默认值)
    * 如果有值返回值，如果没有返回默认值
    */
  def map3: Unit ={
    val map: mutable.HashMap[String, Int] = collection.mutable.HashMap[String, Int]()
    map.put("hello", 10)
    map.put("woya", 14)

    println("添加元素："+ map.getOrElse("hello",12)) //添加元素
  }

}
