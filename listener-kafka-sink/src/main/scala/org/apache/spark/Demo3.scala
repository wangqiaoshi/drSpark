package org.apache.spark

import org.quartz.impl.StdSchedulerFactory

/**
  * Created by wqs on 16/12/12.
  */
object Demo3 {


  def main(args: Array[String]) {


    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    scheduler.resumeAll()
    scheduler.start()



  }

}
