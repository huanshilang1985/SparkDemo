package com.zh.scala.s3_class

/**
  * 主构造器
  * 在类名后面加属性，就是主构造器
  * 如果主构造器中成员变量属性没有被val var修饰的话，该属性不能被访问
  * 如果成员属性被var修饰的话，相当于java中对外提供了get方式和set方式
  * 如果成员属性被val修饰的话，相当于java中对外只提供了get方式
  * 2019/3/8 22:02
  */
class c4_Person2(var name: String, age: Int) {


}

object person2_Test extends App {
  val p = new c4_Person2("zhanghe", 19)
  print("主构造器：")
  println(p.name)
//  println(p.age)
}
