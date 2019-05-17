package com.zh.java.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @Author zhanghe
 * @Desc: Java版本的WordCount
 * @Date 2019/4/18 15:49
 */
public class JavaWordCount {

    public static void main(String[] args) {
        String path = JavaWordCount.class.getClassLoader().getResource("wc.txt").getPath();

        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //读取数据
        JavaRDD<String> lines = sc.textFile(path);

        //分词第一个参数表示读进来的话，第二个参数表示返回值
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String input) throws Exception {
                return Arrays.asList(input.split(" ")).iterator();
            }
        });

        // 每个单词记录一次
        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String input) throws Exception {
                return new Tuple2<String, Integer>(input, 1);
            }
        });
        // 执行reduce操作
        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        // 输出结果
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<String, Integer> tuple : output) {
            System.out.println(tuple._1 + ": " + tuple._2);
        }
        // 关闭资源
        sc.stop();
    }
}
