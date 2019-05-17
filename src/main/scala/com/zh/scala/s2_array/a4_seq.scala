package com.zh.scala.s2_array

/**
  * 序列
  * 2019/3/6 21:59
  */
object a4_seq {

  def main(args: Array[String]): Unit = {
    //    list_count
//        list_sortby
//        group
    //    list_fold
//    zip
    list_sum
  }


  /**
    * 声明空集合
    */
  def list_nil: Unit = {
    val list = Nil
    println(list.toBuffer)
    println("空列表头部加元素1：" + (4 :: list).toBuffer)
    println("空列表头部加元素2：" + (5 :: Nil).toBuffer)
  }

  /**
    * 获取集合head信息
    * tail信息，除了head都是尾部
    */
  def list_head: Unit = {
    var list = List(2, 3, 4, 5)
    println("head信息" + list.head)
    println("tail信息" + list.tail)
  }

  /**
    * 两个集合相加
    */
  def list_add: Unit = {
    val list = List(1, 2, 3)
    print("一个+号，是拼接字符串：" + "aaa")
    println("两个集合相加：" + (list ++ List(4, 5)).toBuffer)
    println("合并集合：" + (list ++: List(5.6)).toBuffer)
    println("头部追加元素：" + (list.+:(10)).toBuffer)
    println("尾部追加元素：" + (list :+ (10)).toBuffer)
    println("两个集合相加：" + (list ::: List(5.6)).toBuffer)
  }

  /**
    * count 统计个数
    * filter 过滤
    */
  def list_count: Unit = {
    val list = List(1, 2, 3, 4, 5);
    println("统计大于3的个数：" + (list.count(x => x > 3)))
    println("过滤大于等于4的集合数据：" + (list.filter(x => x >= 4)))
  }

  /**
    * sortBy 排序
    * sortWith 排序
    */
  def list_sortby: Unit = {
    val list = List(3, 5, 1, 2, 7, 6)
    println("SortBy结果：" + (list.sortBy(x => x)))
    println("降序结果：" + (list.sortBy(x => -x)))

    var l2 = List(("h", 2), ("r", 1), ("m", 4))
    println("Sort结果：" + (l2.sortBy(x => x._2))) //x._2根据第二个元素排序
    println("Sort结果：" + (l2.sortBy(x => -x._2))) //根据第二个元素排序
    println("SortWith结果：" + (l2.sortWith((x, y) => x._2 > y._2))) //SortWith同时对两个数据进行对比排序
  }

  /**
    * group 分组，用法与数组相同
    * grouped
    */
  def group(): Unit = {
    var list = List(("h", 2), ("r", 1), ("m", 4))
    //2个元素分一组，grouped分组返回的是迭代器，所以后面要toList
    println("分组结果：" + list.grouped(2).toList)
    //List(List((h,2), (r,1)), List((m,4)))
  }

  /**
    * fold 折叠
    */
  def list_fold(): Unit = {
    val list = List(1, 2, 3)
    //fold(0)() 第一个括号内的是初始值， 第二个括号是自定义的折叠公式
    //默认左折叠
    println("折叠结果：" + (list.fold(0)((x, y) => x + y)))
    println("折叠结果：" + (list.fold(1)((x, y) => x + y)))
    println("折叠结果：" + (list.fold(2)((x, y) => x + y)))

    //右折叠
    println("折叠结果：" + (list.foldRight(2)((x, y) => x - y)))

    //折叠简化方法
    println("折叠简化方法：" + (list.fold(2)(_ + _)))
    println("折叠简化方法：" + (list.foldRight(2)(_ + _)))
    println("折叠简化方法：" + (list.fold(0)(_ - _))) //  ((0-1)-2)-3
    println("折叠简化方法：" + (list.foldRight(0)(_ - _))) //  1-(3-(4-0))  0是初始值
  }

  /**
    * 集合
    */
  def list_reduce: Unit = {
    val list = List(1, 2, 3)
    println("聚合结果：" + list.reduce(_ + _)) //1+2+3
    println("聚合结果：" + list.reduce(_ - _)) //1-2-3
  }

  /**
    * 先局部聚合再全局聚合
    * 分布式函数，底层用的是foldLeft实现
    */
  def aggregate: Unit = {
    val list = List(1, 2, 3)
    println("聚合结果：" + list.aggregate(0)(_ + _, _ + _))
  }

  /**
    * 拉链
    */
  def zip: Unit = {
    val list1 = List(1, 2, 3)
    val list2 = List(6, 7, 8)
    println("拉链结果：" + list1.zip(list2))
    //List((1,6), (2,7), (3,8))
  }

  def list_sum: Unit = {
    val list = List(1, 2, 3)
    println("sum结果：" + list.sum)
  }

}
