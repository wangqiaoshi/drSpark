package org.apache.spark

import java.util.Date

import org.quartz._
import org.quartz.impl.{JobDetailImpl, StdSchedulerFactory}
import org.quartz.CronScheduleBuilder
import org.quartz.impl.matchers.EverythingMatcher
import org.quartz.listeners.JobChainingJobListener

/**
  * Created by wqs on 16/11/30.
  */
object SchedulerDemo {



  def main(args: Array[String]) {
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler

//    val myJobListener = new MyJobListener("myLis")
//    scheduler.getListenerManager.addJobListener(myJobListener,EverythingMatcher.allJobs())



    val chainListener = new MyJobChainingJobListener("chainListner")
//
//    val job1 = new JobKey("job1","group1")
//    val job2 = new JobKey("job2","group1")
//    chainListener.addJobChainLink(job1,job2)
    scheduler.getListenerManager.addJobListener(chainListener,EverythingMatcher.allJobs())




//    deleteTrigger(scheduler)
//    deleteJob(scheduler)


//    val job1 = JobBuilder.newJob(classOf[PrintPropsJob]).usingJobData("key","job1")
//      .withIdentity("job1", "group1").build()
//    val runTime = new Date();
//    val trigger = TriggerBuilder.newTrigger()
//      .withIdentity("trigger1", "group1")
//        .withSchedule(CronScheduleBuilder.cronSchedule("10/1 * * * * ?"))
//      .startNow().build()



//    val job2 = JobBuilder.newJob(classOf[PrintPropsJob]).usingJobData("key","job2")
//        .storeDurably()
//      .withIdentity("job2", "group1").build()
//    val trigger2 =  TriggerBuilder.newTrigger().withIdentity("trigger2","group1")
//      .withSchedule(CronScheduleBuilder.cronSchedule("10/1 0/1 * * * ?"))
//      .startNow().build()



//    scheduler.scheduleJob(job1,trigger)
//    scheduler.addJob(job2,false)

//    val job = scheduler.getJobDetail(new JobKey("job1","group1"))

//    val trigger2 =  TriggerBuilder.newTrigger().withIdentity("trigger2","group1")
//     .startNow().build()
//    scheduler.scheduleJob(job,trigger2)

//        val job1 = JobBuilder.newJob(classOf[PrintPropsJob])
//              .usingJobData("key","tempjob").storeDurably()
//          .withIdentity("job2", "group1").build()
//    scheduler.addJob(job1,false)
//    val jobMap = new JobDataMap()
//    jobMap.put("key","qsqsqs")
//    scheduler.triggerJob(new JobKey("job1","group1"),jobMap)



    scheduler.start()

  }

  def deleteJob(scheduler:Scheduler): Unit ={
    scheduler.deleteJob(new JobKey("job1","group1"))
    scheduler.deleteJob(new JobKey("job2","group1"))
  }

  def deleteTrigger(scheduler:Scheduler): Unit ={

    scheduler.unscheduleJob(new TriggerKey("trigger2","group1"))

  }

}
