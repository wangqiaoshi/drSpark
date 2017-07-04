package org.apache.spark

import java.text.SimpleDateFormat

import org.quartz.{Job, JobExecutionContext}
import java.util.Date
/**
  * Created by wqs on 16/12/13.
  */
class LongJob extends  Job{

  val sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  override def execute(context: JobExecutionContext): Unit = {

    println(sf.format(new Date()))
    Thread.sleep(10000)
  }


}
