package com.tansuo.computer.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lifei on 16/10/19.
  *
  * 本地测试
  *  cd ~/githubproject/tansuo/tansuo/out/artifacts/hello
  *  spark-submit  --master local --name wordCountByScala --class com.tansuo.computer.test.WordCount ./hello.jar file:///Users/lifei/tmp/test.log
  */
object WordCount{
  def main(args: Array[String]) {
    if (args.length < 1){
      System.out.println("Usage:<file>")
      System.exit(1)
    }
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val line = sc.textFile(args(0))
    line.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect().foreach(println)
    sc.stop()
  }
}

