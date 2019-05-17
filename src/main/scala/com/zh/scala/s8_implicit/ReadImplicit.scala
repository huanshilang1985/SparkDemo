package com.zh.scala.s8_implicit

import java.io.File

import scala.io.Source

/**
  * 定义隐式类
  * 2019/3/14 20:11
  */
object ReadImplicit {

  /**
    * 隐式类
    * Source.fromFile 是读取文件方法
    * mkString 把内容转成String
    *
    * @param file
    */
  implicit class FileRead(file: File) {
    def read: String = Source.fromFile(file).mkString
  }

  def main(args: Array[String]): Unit = {
    val file = new File("D:/app/Logs/dxh-nwd-rpc/error.log")
    println(file.read)
  }

}
