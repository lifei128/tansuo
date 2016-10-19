package com.tansuo.computer.test

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by lifei on 16/10/19.
  *
  *  spark-submit  --master local --name json4sqlByScala --class com.tansuo.computer.test.Json4Sql   ./hello.jar  file:///Users/lifei/tmp/jsonfile.txt
  *
  *
  */
object Json4Sql{
  def main(args: Array[String]) {
    if(args.length < 1){
      System.out.println("Usage:<file>")
      System.exit(1)
    }
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val df = sqlContext.read.json(args(0))
//    df.show()

//    df.registerTempTable("city_info")
//    val city = sqlContext.sql("select city,cnty,id,lat,lon,prov from city_info where prov='西1'")
//    city.foreach(println)

    df.select(df("prov"),df("city")).show()
  }

  /**
    *
{ "city": "萨嘎", "cnty": "中国", "id": "CN101140209", "lat": "29.318000", "lon": "85.222000", "prov": "西1" }
{ "city": "吉隆", "cnty": "中国", "id": "CN101140210", "lat": "28.858000", "lon": "85.311000", "prov": "西2" }
{ "city": "昂仁", "cnty": "中国", "id": "CN101140211", "lat": "29.296000", "lon": "87.238000", "prov": "西3" }
{ "city": "定结", "cnty": "中国", "id": "CN101140212", "lat": "29.230000", "lon": "89.775000", "prov": "西4" }
    */
}
