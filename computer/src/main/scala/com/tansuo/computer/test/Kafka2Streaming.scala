package com.tansuo.computer.test

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by lifei on 16/10/19.
  *
  * echo "hhhhh h h h h h h h h 888d f s d f ds" >> 1.txt
  *
  * spark-submit  --master local --name kafkastreaming  --class com.tansuo.computer.test.Kafka2Streaming  ./computer-jar-with-dependencies.jar localhost:2181 group mykafka kafka2StreamingTest 2
  *
  *
  */
object Kafka2Streaming {

  def main(args: Array[String]): Unit = {

    if (args.length < 5) {
      System.err.println("Usage: QunarSparkTest <zkQuorum> <group> <topics> <appName> <numThreads>")
      System.exit(1)
    }
    // 如果需要给executor设置环境变量，请在这里设置
    //System.setProperty("HADOOP_USER_NAME", "qhstats")

    val Array(zkQuorum, group, topics, appName, numThreads) = args

    val sparkConf = new SparkConf().setAppName(appName)
      //必须为true因为我们只能使用coarse模式
      .set("spark.mesos.coarse", "true")
      //N*2表示开N个executor，能少开尽量少开
      .set("spark.cores.max", "4")
      //读kafka的线程数，建议开2线程。
      .set("spark.default.parallelism", "2")
      // 产生每个block的时间决定任务数量，如果设得大则任务少，设的少则任务多，根据情况来设
      .set("spark.streaming.blockInterval", "500ms")
      // 这个可压可不压没什么大影响的
      .set("spark.rdd.compress", "true")
    //流控的一种方式，其实我推荐如果数据量不是很大可以不流控
    // .set("spark.streaming.receiver.maxRate", "2500")
    // 如果需要本地跑测试，请把这个打开一
     .setMaster("local[2]")


    val sc = new SparkContext(sparkConf)
    //每1秒一个batch
    val ssc = new StreamingContext(sc, Seconds(2))

    //务必以spark开头
//    val statsD = new NonBlockingStatsDClient("spark." + appCode, "localhost", 8125)

    System.setProperty("file.encoding", "UTF-8")
    System.setProperty("SPARK_APP_CODE", appName)

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    //这里是决定开多少个receiver，这里写的是1,开receiver数是由每个slave开多少个executor，还有kafka开了多少partition共同决定的
    //如果开的过多可能会有问题，开得少有可能并行不充分
//    val readParallelism = 4
//
//    val kafkaDStreams = (1 to readParallelism).map { _ =>
//      KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK)
//    }
//    // 这里是不使用多个receiver的情况
//
//    val unionDStream = ssc.union(kafkaDStreams)
//
//    val lines = unionDStream.map(_._2)
//    println("中文测试")
//
//    //foreachRDD模式，推荐用使用
//    lines.foreachRDD(rdd => {
//      rdd.foreach(
//        //在foreach里是每个分布式应用里的最小单元所做的工作。
//        event => {
//          println("deal a message")
//          println("中文测试")
//        }
//      )
//    }
//    )
      val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK).map(_._2)
      val words = lines.flatMap(_.split(" "))
      //得到每个批次的wordcount
      val wordCounts = words.map(x => (x, 1)).reduceByKey(_+_)
      //打印三行
      wordCounts.print(3)
      //
      wordCounts.foreachRDD(rdd =>
      {
        rdd.foreachPartition(p =>{
          System.out.println("*****************************")
          p.foreach(println)
          System.out.println("*****************************")
        })
      })
    ssc.start()

    while(true){
      Thread.sleep(6000)
    }
  }
}
