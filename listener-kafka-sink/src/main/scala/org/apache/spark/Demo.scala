package org.apache.spark

/**
  * Created by wqs on 16/12/15.
  */
object Demo {

  def main(args: Array[String]) {


    Thread.sleep(500)
    Runtime.getRuntime.addShutdownHook(new Thread{

      override def run(): Unit ={
        println("xxx")

      }
    })

  }
}
