package com.zh.spark.day5_sparkSql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * SparkSQL的三种方式
  */
object Sql2 {

  def main(args: Array[String]): Unit = {

  }

  case class Emp(empno: Int, ename: String, job: String, mgr: Int, hiredate: String, sal: Int, comm: Int, deptno: Int)

  /**
    * SparkSQL方式1：使用Case Class映射成对象，返回
    */
  def beanToSql(): Unit ={
    //文件路径
    val path: String = getClass.getClassLoader.getResource("emp.csv").getFile
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("SqlTestDSL").master("local[2]").getOrCreate()
    val lines: RDD[Array[String]] = sparkSession.sparkContext.textFile(path).map(_.split(","))
    //把每行数据，映射到Emp上
    val allEmp: RDD[Emp] = lines.map(x => Emp(x(0).toInt,x(1),x(2),x(3).toInt,x(4),x(5).toInt,x(6).toInt,x(7).toInt))

    val df: DataFrame = sparkSession.createDataFrame(allEmp)
    df.show()
  }

  /**
    * SparkSQL方式2：自定义Schema做映射
    */
  def structToSql():Unit = {
    //文件路径
    val path: String = getClass.getClassLoader.getResource("emp.csv").getFile
    // 创建SparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("SqlTestDSL").master("local[2]").getOrCreate()
    val lines: RDD[Array[String]] = sparkSession.sparkContext.textFile(path).map(_.split(","))
    val allEmp:RDD[Row] = lines.map(x => Row(x(0).toInt,x(1),x(2),x(3).toInt,x(4),x(5).toInt,x(6).toInt,x(7).toInt))
    val myschema: StructType = StructType(
      List(
        StructField("empno", DataTypes.IntegerType),
        StructField("ename", DataTypes.StringType),
        StructField("job", DataTypes.StringType),
        StructField("mgr", DataTypes.IntegerType),
        StructField("hiredate", DataTypes.StringType),
        StructField("sal", DataTypes.IntegerType),
        StructField("comm", DataTypes.IntegerType),
        StructField("deptno", DataTypes.IntegerType)
      )
    )
    val df:DataFrame = sparkSession.createDataFrame(myschema)
    df.show()
  }

  /**
    * SparkSQL方式3：读取带格式的文件，直接生成，比如JSON
    */
  def readJson():Unit = {
    val path: String = getClass.getClassLoader.getResource("people.json").getFile
    val sparkSession: SparkSession = SparkSession.builder().appName("SqlTestDSL").master("local[2]").getOrCreate()

    //读取Json方式1
    val df:DataFrame = sparkSession.read.json(path)
    //读取Json方式2
//    val df:DataFrame = sparkSession.read.format("json").load(path)
    df.show()
  }

}

