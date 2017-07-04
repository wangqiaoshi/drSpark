package org.apache.spark

import org.quartz.{Job, JobExecutionContext}

import java.util.Date
/**
  * Created by wqs on 16/11/30.
  */
class PrintPropsJob extends Job {


  override def execute(context: JobExecutionContext): Unit = {


    val data = context.getMergedJobDataMap();
    println(data.get("key"))
//    System.out.println("someProp = " + data.getString("someProp"));
  }


}
