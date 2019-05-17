package com.zh.scala.s10_comparable

/**
  * <% 视图界定，发生了隐式转换
  * < ： 上届，不会自动转换
  * 2019/3/14 22:04
  */
class ComparaCC[T <% Comparable[T]](o1:T, o2:T){
  def big = if(o1.compareTo(o2) > 0 ) o1 else o2
}

object ViewBounds {
  def main(args: Array[String]): Unit = {
    val comc = new ComparaCC(1, 2)
    println(comc.big)
  }
}
