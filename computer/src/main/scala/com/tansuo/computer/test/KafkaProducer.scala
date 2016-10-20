package com.tansuo.computer.test

import java.util

import org.apache.kafka.clients.producer.{ProducerConfig, ProducerRecord, KafkaProducer}

/**
  *
  * Created by lifei on 16/10/20.
  */
object KafkaProducer {

  def main(args: Array[String]): Unit = {
    val topic = "mykafka"
    val brokers = "localhost:9092"
    val messagesPerSec = 10 //每秒生产10个message
    val wordsPerMessage = 10 //每个message10个word

    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    while (true) {
      (1 to messagesPerSec.toInt).foreach {
        messageNum =>
          val str = (1 to wordsPerMessage.toInt).map(x => scala.util.Random.nextInt(10).toString)
            .mkString(" ")
          val message = new ProducerRecord[String, String](topic, null, str)
          producer.send(message)
      }
      Thread.sleep(1000)
    }
  }
}
