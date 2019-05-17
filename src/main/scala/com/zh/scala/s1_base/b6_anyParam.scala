package com.zh.scala.s1_base

/**
  * @author zhanghe
  * 可变参数函数
  * 2019/3/4 20:04
  */
object b6_anyParam {

  def main(args: Array[String]): Unit = {
    println(sum(1,2,4))
    println(sum(123,21))
    println(setName("zhanghe", 19, 111))
  }

  /**
    * 可变参数函数：数据类型后加上通配符*
    * @param ints
    * @return
    */
  def sum(ints:Int*): Int={
    var sum = 0
    for (v <- ints){
      sum += v
    }
    sum
  }

  /**
    * 多参数类型的可变参数
    * @param params
    * @return
    */
  def setName(params : Any*): Any ={
    return params
  }

}
