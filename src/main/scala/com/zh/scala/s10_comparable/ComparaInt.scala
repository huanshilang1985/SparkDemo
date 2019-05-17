package com.zh.scala.s10_comparable

//定义一个比较方式
class ComparaInt(a:Int, b:Int) {
  def compara = if (a > b) a else b
}

//定义比较类型
class ComparaC[T  <: Comparable[T]](o1:T,o2:T){
  def compara = if(o1.compareTo(o2) > 0) o1 else o2
}

object UpperBounds {
  def main(args: Array[String]): Unit = {
    val big = new ComparaInt(1,2)
    println(big.compara)

    val comc = new ComparaC(Integer.valueOf(1),Integer.valueOf(2))
    println(comc.compara)
  }
}