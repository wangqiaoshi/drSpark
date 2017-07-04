package org.apache.spark

import org.quartz.{JobKey, SchedulerException, Trigger}
import org.quartz.impl.jdbcjobstore.JobStoreTX
import org.quartz.simpl.CascadingClassLoadHelper
import org.quartz.spi.SchedulerSignaler
import org.quartz.impl.jdbcjobstore.JobStoreSupport
import org.quartz.utils.{ConnectionProvider, DBConnectionManager, PoolingConnectionProvider}

/**
  * Created by wqs on 16/12/15.
  */
object TestJobStoreTX {


  def main(args: Array[String]) {

    func1()
  }

  def func2(): Unit ={


  }



  def func1(): Unit ={

    Class.forName("com.mysql.jdbc.Driver")

    val connectionProvider = new PoolingConnectionProvider(
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://192.168.6.52:3306/quartz",
      "quartz", "quartz",30,"")
    DBConnectionManager.getInstance().addConnectionProvider("quartzDataSource",connectionProvider)

    val sampleSignaler = new SampleSignaler()
    val loadHelper = new CascadingClassLoadHelper()
    loadHelper.initialize()

    val jobStoreTX = new JobStoreTX()
    jobStoreTX.setDataSource("quartzDataSource")
    jobStoreTX.initialize(loadHelper,sampleSignaler)

    jobStoreTX.setTablePrefix("QRTZ_");
    jobStoreTX.setInstanceId("NON_CLUSTERED")
    jobStoreTX.setInstanceName("QuartzScheduler")
    jobStoreTX.setUseDBLocks(true)




    jobStoreTX.schedulerStarted()


  }

  class SampleSignaler extends SchedulerSignaler {
     var fMisfireCount: Int = 0

    def notifyTriggerListenersMisfired(trigger: Trigger) {
      System.out.println("Trigger misfired: " + trigger.getKey + ", fire time: " + trigger.getNextFireTime)
      fMisfireCount += 1
    }

    def signalSchedulingChange(candidateNewNextFireTime: Long) {
    }

    def notifySchedulerListenersFinalized(trigger: Trigger) {
    }

    def notifySchedulerListenersJobDeleted(jobKey: JobKey) {
    }

    def notifySchedulerListenersError(string: String, jpe: SchedulerException) {
    }
  }




}
