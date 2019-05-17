package com.zh.scala.s8_implicit

import java.io.File

/**
  * 扫描文件的数据条数
  * scala可以兼容java的jar包
  * 2019/3/14 19:56
  */
object FileMain {

  /**
    * 定义隐式转换
    * file隐式转换了RichFile，直接使用了RichFile的方法
    */
  implicit def file2RichFile(file:File) = new RichFile(file)

  def main(args: Array[String]): Unit = {
    //1. 加载文件
    val file = new File("D:/app/Logs/dxh-nwd-rpc/error.log")
    //2. 打印条数
    println(file.count())
  }

}
