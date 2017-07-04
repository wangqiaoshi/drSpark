package org.apache.spark

import org.quartz.{Job, JobExecutionContext, JobExecutionException}

/**
  * Created by wqs on 16/12/13.
  */
class BadJob2 extends Job{

  override def execute(context: JobExecutionContext): Unit = {

    try{
      val  zero = 0
      val calculation = 4815 / zero;


    }
    catch {
      case e:Exception=>
        println("error")
        val jobExecutionError = new JobExecutionException(e)
        jobExecutionError.setUnscheduleAllTriggers(true)
        throw jobExecutionError

    }

  }
}
