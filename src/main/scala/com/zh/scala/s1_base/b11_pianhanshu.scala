package com.zh.scala.s1_base

/**
  * 偏函数：被包在花括号内没有match的一组case语句的是一个偏函数。
  * 2019/3/4 20:41
  */
object b11_pianhanshu {

  def main(args: Array[String]): Unit = {
    println(func("zhanghe"))
    println(func2("zhanghe"))
  }

  /**
    * 正常函数
    */
  def func(str: String): Int ={
    if(str.equals("zhanghe")){
      18
    } else {
      0
    }
  }

  /**
    * 偏函数
    */
  def func2:PartialFunction[String, Int] = {
    //如果使用了偏函数必须用case
    case "zhanghe" => 18
    case _ => 0
  }

}
