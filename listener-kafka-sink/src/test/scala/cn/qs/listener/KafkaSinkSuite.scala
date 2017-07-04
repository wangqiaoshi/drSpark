package cn.qs.listener


import org.apache.spark.{KafkaListener, SparkConf, SparkContext}

/**
  * Created by wqs on 16/10/15.
  */
class KafkaSinkSuite {

}

object KafkaSinkSuite{

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("fp_flume").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val kafkaListener = new KafkaListener
    sc.addSparkListener(kafkaListener)
    val rdd = sc.parallelize(1 to 100, 4)

    sc.makeRDD(Seq(scala.collection.mutable.Map("xx"->"xx")))
    rdd.count()
  }
}
