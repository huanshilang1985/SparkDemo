package com.zh.scala.s7_caseClass

/**
  * 样例类
  * 2019/3/12 22:49
  */
//样例类支持模式匹配
case class Boy(high:Int, wight:Int)
case class Girl(high:Int, wight:Int)

object CaseTest {

  def objMatch(obj:Any):Unit = obj match {
    case Boy(x,y) => println(s"$x $y 的男孩")
    case Girl(x,y) => println(s"$x $y 的nv孩")
  }

  def main(args: Array[String]): Unit = {
    CaseTest.objMatch(Boy(15,16))
  }

}
