package com.zh.scala.s2_array

/**
  * 数组的map,flatten, flatMap
  * 2019/3/6 17:42
  */
object a2_map {

  def main(args: Array[String]): Unit = {
    arr_flatMap()
  }

  /**
    * 数组的map方法，map内要使用的函数
    */
  def arr_map(): Unit = {
    //1. 定义数组
    val arr1 = Array(1, 2, 3)
    val arr2 = Array.apply(4, 5, 6) //apply是创建数组的方法，缺省方法
    //2. 调用map方法，会用一个新数组接收map只
    val arr3 = arr1.map((x: Int) => x * 10)
    println(arr3.toBuffer)
  }

  /**
    * 扁平化操作
    */
  def arr_flatten(): Unit = {
    val arr = Array("hello zhanghe hen shuai", "hello haha hen mei")
    println("元素数量：" + arr.length)

    //Array<Array<hello,zhanghe,hen,shuai>, Array<hello,haha,hen,mei>>
    var temp = arr.map(_.split(" "))
    println("map方法切分的结果：" + temp.toBuffer)

    //Array<hello,zhanghe,hen,shuai,hello,haha,hen,mei>
    var temp2 = arr.map(_.split(" ")).flatten
    println("flatten方法之后的结果：" + temp2.toBuffer)
  }

  /**
    * flatMap方法= map + flatten
    */
  def arr_flatMap(): Unit = {
    val arr = Array("hello zhanghe hen shuai", "hello haha hen mei")
    var temp = arr.flatMap(_.split(" "))
    println("flatMap方法之后的结果：" + temp.toBuffer)
  }

  /**
    * 数组遍历
    */
  def arr_foreach(): Unit ={
    val arr = Array("hello zhanghe hen shuai", "hello haha hen mei")
    arr.foreach(x => println(x))
  }

  /**
    * 数组 分组
    */
  def arr_groupby(): Unit ={
    val arr = Array("hello zhanghe hen shuai", "hello haha hen mei")
    var temp = arr.flatMap(_.split(" ")).groupBy(x => x)
    //结果：scala.collection.immutable.Map[String,Array[String]] = Map(hen -> Array(hen, hen), shuai -> Array(shuai), mei -> Array(mei), zhanghe -> Array(zhanghe), haha -> Array(haha), hello -> Array(hello, hello))

    //x表示接收的数据，包括任何数据类型，x._1 表示x的第一个元素
    //实现wordCount
    var temp2 = arr.flatMap(_.split(" ")).groupBy(x => x).map(x => (x._1,x._2.length))
    //结果：scala.collection.immutable.Map[String,Int] = Map(hen -> 2, shuai -> 1, mei -> 1, zhanghe -> 1, haha -> 1, hello -> 2)
  }

  /**
    * 排序，默认升序排序
    */
  def arr_sortby: Unit ={
    val arr = Array("hello zhanghe hen shuai", "hello haha hen mei")
    var temp = arr.flatMap(_.split(" ")).groupBy(x => x).map(x => (x._1,x._2.length)).toList.sortBy(x => x._2)
    //List[(String, Int)] = List((shuai,1), (mei,1), (zhanghe,1), (haha,1), (hen,2), (hello,2))

    //这是倒序，使用-号
    var temp2 = arr.flatMap(_.split(" ")).groupBy(x => x).map(x => (x._1,x._2.length)).toList.sortBy(x => -x._2)
  }

}
