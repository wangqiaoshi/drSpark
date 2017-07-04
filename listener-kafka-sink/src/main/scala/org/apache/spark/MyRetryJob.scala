package org.apache.spark

import org.quartz.{Job, JobExecutionContext, JobExecutionException}

/**
  * Created by wqs on 16/12/13.
  */
class MyRetryJob  extends  Job{



  override def execute(context: JobExecutionContext): Unit = {
    val dataMap = context.getMergedJobDataMap
    val retry = dataMap.getInt("retry")
    var retryCount = dataMap.getOrDefault("retryCount",new Integer(1)).asInstanceOf[Integer]

    try{
        println("exec "+(retryCount))
        retryCount = retryCount+1

        dataMap.put("retryCount",retryCount)
        throw new Exception("just for test exception")
    }catch {
      case e:Exception=>
        val execError = new JobExecutionException(e)
        if(retryCount<=retry){
          execError.setRefireImmediately(true)
        }
        else{
          execError.setUnscheduleAllTriggers(true)
        }
        throw  execError
    }

  }


}
