package org.apache.spark

import java.util.Properties

import org.quartz.impl.StdSchedulerFactory

/**
  * Created by wqs on 16/12/2.
  */
object SchedulerDemo2 {


  def main(args: Array[String]) {

    val properties = new Properties()
    properties.setProperty("org.quartz.threadPool.threadCount","5")
    properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool")
    properties.setProperty("org.quartz.scheduler.instanceId", "AUTO")

    val sf = new StdSchedulerFactory(properties)
    val scheduler = sf.getScheduler()






  }

}
