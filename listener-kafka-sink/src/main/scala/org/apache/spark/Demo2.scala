package org.apache.spark

import scala.io.Source

/**
  * Created by wqs on 16/12/12.
  */
object Demo2 {


  def main(args: Array[String]) {


   val process =  Runtime.getRuntime.exec("java -cp /Users/wqs/workplace/dr-spark/listener-kafka-sink/target/kafka-sink.jar org.apache.spark.DemoJava xxxx")
    val stderr = process.getErrorStream
    val stdout = process.getInputStream
    Source.fromInputStream(stderr).getLines().foreach(line=>println(line))
    Source.fromInputStream(stdout).getLines().foreach(line=>println(line))


  }

}
