package kafka

import config.Config._
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

class KafkaMessageProducer {

  def initKafkaProducer[K, V]: KafkaProducer[K, V] = {
    val props = new java.util.Properties()
    println(KafkaProducerConfigLocation)
    val resource = this.getClass.getResourceAsStream(KafkaProducerConfigLocation)
    props.load(resource)
    new KafkaProducer[K, V](props)
  }

  def createKafkaMessage(event: String): Unit = {

    val message = new ProducerRecord(KafkaTopicName, event)
    this.kafkaProducer.send(message)

  }

  val kafkaProducer = initKafkaProducer[Nothing, String]
}
