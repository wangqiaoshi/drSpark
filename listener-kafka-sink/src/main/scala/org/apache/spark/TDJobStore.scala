package org.apache.spark

import java.util.Collection
import java.util.List
import java.util.Set
import java.util.Map

import org.quartz.Trigger.{CompletedExecutionInstruction, TriggerState}
import org.quartz.impl.matchers.GroupMatcher
import org.quartz._
import org.quartz.impl.jdbcjobstore.JobStoreTX
import org.quartz.spi._

/**
  * Created by wqs on 16/12/19.
  */


class TDJobStore extends JobStoreTX {


  override def checkExists(jobKey: JobKey): Boolean = {
    false
  }

  override def checkExists(triggerKey: TriggerKey): Boolean = {
    false
  }

  override def resumeAll(): Unit = {

  }

  override def retrieveCalendar(calName: String): Calendar = {
    null
  }

  override def shutdown(): Unit = {

  }

  override def getTriggerState(triggerKey: TriggerKey): TriggerState = {
    null
  }

  override def getNumberOfCalendars: Int = {
    super.getNumberOfCalendars
  }




  override def getTriggerGroupNames: List[String] = {

    null
  }

  override def getPausedTriggerGroups: Set[String] = {

    null
  }

  override def removeTrigger(triggerKey: TriggerKey): Boolean = {

    super.removeTrigger(triggerKey)
  }



  override def retrieveTrigger(triggerKey: TriggerKey): OperableTrigger = {

    super.retrieveTrigger(triggerKey)
  }

  override def getNumberOfTriggers: Int = {

    super.getNumberOfTriggers
  }

  override def storeCalendar(name: String, calendar: Calendar, replaceExisting: Boolean, updateTriggers: Boolean): Unit = {

  }

  override def replaceTrigger(triggerKey: TriggerKey, newTrigger: OperableTrigger): Boolean = {

    super.replaceTrigger(triggerKey,newTrigger)
  }

  override def storeJobAndTrigger(newJob: JobDetail, newTrigger: OperableTrigger): Unit = {

  }

  override def removeJob(jobKey: JobKey): Boolean = {
    return false
  }

  override def getCalendarNames: List[String] = {

    return null
  }

  override def getJobKeys(matcher: GroupMatcher[JobKey]): Set[JobKey] = {

    super.getJobKeys(matcher)
  }

  override def triggeredJobComplete(trigger: OperableTrigger, jobDetail: JobDetail, triggerInstCode: CompletedExecutionInstruction): Unit = ???

  override def getJobGroupNames: List[String] = {
    return null;
  }

  override def schedulerPaused(): Unit = {

  }

  override def pauseAll(): Unit = {

  }

  override def schedulerResumed(): Unit = {

  }

  override def getEstimatedTimeToReleaseAndAcquireTrigger: Long = ???

  override def pauseJob(jobKey: JobKey): Unit = ???

  override def pauseTrigger(triggerKey: TriggerKey): Unit = ???

  override def storeJobsAndTriggers(triggersAndJobs: Map[JobDetail, Set[_ <: Trigger]], replace: Boolean): Unit = {

  }

  override def pauseTriggers(matcher: GroupMatcher[TriggerKey]): Set[String] = {

    super.pauseTriggers(matcher)

  }

  override def supportsPersistence(): Boolean = {

    false
  }

  override def removeTriggers(triggerKeys: List[TriggerKey]): Boolean = {

    false
  }

  override def clearAllSchedulingData(): Unit = {

    super.clearAllSchedulingData()
  }

  override def getNumberOfJobs: Int = {

    super.getNumberOfJobs
  }



  override def triggersFired(triggers: List[OperableTrigger]): List[TriggerFiredResult] = {

    super.triggersFired(triggers)
  }

  override def removeJobs(jobKeys: List[JobKey]): Boolean = {
    false
  }

  override def isClustered: Boolean = {
    false
  }

  override def retrieveJob(jobKey: JobKey): JobDetail = {
    null
  }

  override def resumeTrigger(triggerKey: TriggerKey): Unit = {

  }

  override def releaseAcquiredTrigger(trigger: OperableTrigger): Unit = {

  }

  override def getTriggersForJob(jobKey: JobKey): List[OperableTrigger] = {

    super.getTriggersForJob(jobKey)
  }

  override def storeJob(newJob: JobDetail, replaceExisting: Boolean): Unit = {

  }



  override def acquireNextTriggers(noLaterThan: Long, maxCount: Int, timeWindow: Long): List[OperableTrigger] = {
    return null
  }

  override def storeTrigger(newTrigger: OperableTrigger, replaceExisting: Boolean): Unit = {

  }

  override def getTriggerKeys(matcher: GroupMatcher[TriggerKey]): Set[TriggerKey] = {
    return null
  }

  override def removeCalendar(calName: String): Boolean = {
    false
  }

}
