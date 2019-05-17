package com.zh.scala.s2_array

/**
  * Set  值不重复
  * 2019/3/7 20:51
  */
object a5_set {

  def main(args: Array[String]): Unit = {
//    set2
    set3
  }

  def set1: Unit = {
    //这个Set是不可变的
    val set = Set(2, 3, 4, 5)
  }

  /**
    * 可变的HashSet
    * += 加元素
    * -= 减元素
    * remove 删除元素
    */
  def set2: Unit = {
    val set = scala.collection.mutable.HashSet(2, 3, 4, 5)
    println("set添加数据：" + (set += 6))
    println("set减数据：" + set.remove(2)) //从Set里减去元素2，返回true,false
    println("set减数据：" + (set -= 3)) //从Set里减去元素3, 返回处理后的Set集合
  }

  /**
    * 集合相加 ++
    * 相加并赋值  ++=
    */
  def set3: Unit = {
    val set1 = scala.collection.mutable.HashSet(2, 3, 4, 5)
    val set2 = scala.collection.mutable.HashSet(6, 7, 8)
    println("两个集合相加：" + (set1 ++ set2))

    println("相加并赋值：" + (set1 ++= Set(4,6)))
  }


}
