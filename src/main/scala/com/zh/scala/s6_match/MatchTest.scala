package com.zh.scala.s6_match

/**
  * 模式匹配
  * match 与case匹配使用
  * 2019/3/12 22:27
  */
object MatchTest {

  /**
    * 匹配字符串
    */
  def strMatch(str:String): Unit = str match {
    case "天空" => println("天空很蓝")
    case "白云" => println("云彩很白")
  }

  /**
    * 匹配数组
    */
  def arrayMatch(arr:Any):Unit = arr match {
    case Array(1) => println("只有一个元素的数组")
    case Array(1,2) => println("有两个元素的数组")
  }

  /**
    * 匹配元组
    */
  def tupleMatch(tuple:Any):Unit = tuple match {
    case(1,_) => println("元组第一个元素为1， 第二个元素任意")
    case("zhanghe",18) => println("张鹤18岁")
  }

  def main(args: Array[String]): Unit = {
    MatchTest.strMatch("天空")
    MatchTest.arrayMatch(Array(1,2))
    MatchTest.tupleMatch(("zhanghe",18)) //对偶元组
  }



}
