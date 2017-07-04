package org.apache.spark

import org.quartz.{Job, JobExecutionContext, JobExecutionException}

/**
  * Created by wqs on 16/12/12.
  */
class RetryJob extends Job{

  override def execute(context: JobExecutionContext): Unit = {
    val dataMap = context.getJobDetail().getJobDataMap()
    var count = dataMap.getIntValue("count")

    // allow 5 retries
    if(count >= 5){

      println("大于5")
      val e = new JobExecutionException("Retries exceeded");
      //make sure it doesn't run again
      e.setUnscheduleAllTriggers(true)
      throw e;
    }


    try{
      //connect to other application etc

      //reset counter back to 0
      println(count)
      throw new Exception("error")
      dataMap.putAsString("count", 0);
    }
    catch{
      case e:Exception=>
      count=count+1
      dataMap.putAsString("count", count);
      val e2 = new JobExecutionException(e)

      Thread.sleep(1000) //sleep for 10 mins

      //fire it again
      e2.setRefireImmediately(true)
      throw e2;
    }
  }


}
