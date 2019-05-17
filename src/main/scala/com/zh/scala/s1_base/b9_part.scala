package com.zh.scala.s1_base

import java.util.Date

/**
  * @author zhanghe
  *         部分参数
  *         2019/3/4 20:30
  */
object b9_part {

  def main(args: Array[String]): Unit = {
    log(new Date(), "haha")
    logMessage("reba")
  }

  def log(date: Date, message: String): Unit = {
    println(s"$date, $message")
  }

  val date: Date = new Date()
  var logMessage: String => Unit = log(date, _: String)


}
