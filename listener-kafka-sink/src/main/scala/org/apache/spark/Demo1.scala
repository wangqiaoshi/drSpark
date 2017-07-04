package org.apache.spark



import org.quartz.{CronScheduleBuilder, JobBuilder, JobKey, TriggerBuilder}
import org.quartz.impl.StdSchedulerFactory
import org.quartz.utils.{ConnectionProvider, DBConnectionManager, PoolingConnectionProvider}

/**
  * Created by wqs on 16/12/4.
  */
object Demo1 {

  def main(args: Array[String]) {

    func7()

  }



  def func9(): Unit ={

    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler()
    scheduler.standby()
//    scheduler.start()


//    Thread.sleep(1000)



  }


  def  func8(): Unit ={
    val provider = new PoolingConnectionProvider("com.mysql.jdbc.Driver",
      "jdbc:mysql://192.168.6.52:3306/quartz", "quartz", "quartz", 10, "")
    DBConnectionManager.getInstance().addConnectionProvider("quartzDataSource",provider)

    val tdJobStore = new TDJobStore
    tdJobStore.setDataSource("quartzDataSource")
    tdJobStore.setInstanceName("QuartzScheduler")
    val triggerJobs = tdJobStore.getTriggersForJob(new JobKey("job14","group1"))
    println(triggerJobs.size())
    println(tdJobStore.getNumberOfJobs)
    println(tdJobStore.getTablePrefix)
    println(tdJobStore.getDataSource)

  }

  def func7(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler()
//    val jobDetail = JobBuilder.newJob(classOf[LongJob])
//      .withIdentity("job15","group1")
//      .storeDurably()
//      .build()
//
//    val trigger = TriggerBuilder.newTrigger().withIdentity("trigger14","group1")
//      .withSchedule(CronScheduleBuilder.cronSchedule("10/1 * * * * ?").withMisfireHandlingInstructionFireAndProceed()).build()
//    scheduler.scheduleJob(jobDetail,trigger)


    scheduler.start()
  }

  def func6(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler()
    val jobDetail = JobBuilder.newJob(classOf[LongJob])
      .withIdentity("job8","group1")
      .storeDurably()
      .build()
    val trigger = TriggerBuilder.newTrigger().withIdentity("trigger8","group1")
      .withSchedule(CronScheduleBuilder.cronSchedule("10/1 * * * * ?").withMisfireHandlingInstructionFireAndProceed()).build()
    scheduler.addJob(jobDetail,true)
    scheduler.triggerJob(new JobKey("job8","group1"))
//    scheduler.scheduleJob(jobDetail,trigger)

    scheduler.start()

  }

  def func5(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[MyRetryJob])
      .usingJobData("retry",new Integer(5))
      .withIdentity("job7","group1")
      .storeDurably()
      .build()
    scheduler.addJob(jobDetail,true)
    scheduler.triggerJob(new JobKey("job7","group1"))

    scheduler.start()
  }

  def func4(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[BadJob2])
      .withIdentity("job6","group1")
      .storeDurably()
      .build()
    scheduler.addJob(jobDetail,true)

    scheduler.triggerJob(new JobKey("job6","group1"))

    scheduler.start()

  }

  def func3(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[BadJob1])
      .withIdentity("job5","group1")
      .storeDurably()
      .build()
    scheduler.addJob(jobDetail,true)

    scheduler.triggerJob(new JobKey("job5","group1"))

    scheduler.start()

  }

  def func1(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[JavaJob])
      .withIdentity("job3","group1")
      .storeDurably().usingJobData("params","xyz")
      .usingJobData("classpath","/Users/wqs/workplace/dr-spark/listener-kafka-sink/target/kafka-sink.jar")
      .usingJobData("class","org.apache.spark.DemoJava").build()
    scheduler.addJob(jobDetail,true)

    scheduler.triggerJob(new JobKey("job3","group1"))
    scheduler.start()

  }

  def func2(): Unit ={
    val sf = new StdSchedulerFactory()
    val scheduler = sf.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[RetryJob])
      .withIdentity("job4","group1")
      .storeDurably().usingJobData("count","0")
         .build()
    scheduler.addJob(jobDetail,true)

    scheduler.triggerJob(new JobKey("job4","group1"))

    scheduler.start()


  }


}
