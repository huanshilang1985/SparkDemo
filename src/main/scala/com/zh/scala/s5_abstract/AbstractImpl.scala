package com.zh.scala.s5_abstract

/**
  * 继承抽象类
  */
class AbstractImpl extends AbstractDemo {
  override def eat(how: String): Unit = {
    //ctrl + i
    println(s"$how 吃猪蹄子")
  }

  override def sleep(how: String): Unit = super.sleep(how)


//  def main(args: Array[String]): Unit = {
//    把class换成object单例类，就可以直接运行main方法
//  }

}

object AbstractImpl extends App {
  val a = new AbstractImpl();
  a.eat("zhanghe")

}
