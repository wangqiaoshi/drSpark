package org.apache.spark

import org.quartz.{JobExecutionContext, JobExecutionException, JobListener}

/**
  * Created by wqs on 16/12/5.
  */
class MyJobListener(name:String)  extends JobListener with Logging  {



  override def getName: String = {
    name
  }

  override def jobExecutionVetoed(context: JobExecutionContext): Unit = {

    println("jobExecutionVetoed")

  }

  override def jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException): Unit ={

    println("jobWasExecuted")
  }

  override def jobToBeExecuted(context: JobExecutionContext): Unit = {
    val group  = context.getJobDetail.getKey.getGroup
    val name = context.getJobDetail.getKey.getName
   println("jobToBeExecuted",group,name)


  }
}
