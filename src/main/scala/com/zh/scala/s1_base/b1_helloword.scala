package com.zh.scala.s1_base

/**
  * zhanghe
  * 2019/3/3 22:38
  */
object b1_helloword {

  //main方法
  def main(args: Array[String]): Unit = {
    val v1:String = "Hello, world"  //定义常量
    var v2:String = "I,m OK"   //定义变量，不加数据类型，Scala会移动判定
    println(v1, v2)


    val x = 2
    val y = if(x > 0) 1 else 2
    println("if判断结果："+ y)

    //Scala支持一句代码混合多种数据类型
    var z = if(x > 0) "error" else 1
    println("z= " + z)

    //Scala中如果缺少else，就是相当于else () ,()表示什么都没有
    var m = if(x >= 2) 1 else ()
    println(m)

    //Scala多个判断条件
    var k = if(x < 0) 0 else if(x >= 1) 1 else -1
    println(k)
  }


}
