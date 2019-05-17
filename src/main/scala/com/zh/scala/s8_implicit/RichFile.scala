package com.zh.scala.s8_implicit

import java.io.{BufferedReader, File, FileReader}

/**
  * 自定义类，读取文件行数
  */
class RichFile(file: File) {

  def count(): Int = {
    //读取数据
    val fileReader: FileReader = new FileReader(file)
    val bufferedReader: BufferedReader = new BufferedReader(fileReader) //缓冲流
    var sum: Int = 0 //计数器
    try {
      while (bufferedReader.readLine() != null) {
        sum += 1
      }
    } catch {
      case _: Exception => sum
    } finally {
      fileReader.close()
      bufferedReader.close()
    }
    sum
  }

}
