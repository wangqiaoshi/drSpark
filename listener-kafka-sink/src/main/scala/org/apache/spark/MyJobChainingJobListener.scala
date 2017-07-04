package org.apache.spark

import java.sql.DriverManager

import Demo1._
import anorm._
import anorm.SqlParser._
import org.quartz.{JobExecutionContext, JobKey}
import org.quartz.listeners.JobChainingJobListener

/**
  * Created by wqs on 16/12/7.
  */

object MyJobChainingJobListener{

  implicit val conn = DriverManager.getConnection("jdbc:mysql://192.168.6.52:3306/quartz","pontus","pontus")

  val chainParse =
    get[String]("jobName") ~
    get[String]("jobGroup") ~
    get[String]("nextJobName") ~
    get[String]("nextJobGroup") map{
      case jobName~jobGroup~nextJobName ~ nextJobGroup=>
        new Chain(jobName,jobGroup ,nextJobName,nextJobGroup)
    }
}

class MyJobChainingJobListener(name:String) extends JobChainingJobListener(name) {

  import MyJobChainingJobListener._

  getChain().foreach(c=>{
    val jobKey = new JobKey(c.jobName,c.jobGroup)
    val nextKey = new JobKey(c.nextJobName,c.nextJobGroup)
    super.addJobChainLink(jobKey,nextKey)
  })



  def getChain(): List[Chain] ={

    val chains = SQL("select * from job_chain").executeQuery().as(chainParse *)
    chains
  }

  private def saveChain(firstJob: JobKey, secondJob: JobKey): Unit ={

    val firstName = firstJob.getName
    val firstGroupName = firstJob.getGroup
    val secondName = secondJob.getName
    val secondGroup = secondJob.getGroup

    SQL("insert into job_chain values({firstName},{firstGroupName},{secondName},{secondGroup})")
      .on("firstName"->firstName,"firstGroupName"->firstGroupName,
        "secondName"->secondName,"secondGroup"->secondGroup).executeInsert()

  }

  override def addJobChainLink(firstJob: JobKey, secondJob: JobKey): Unit = {
    super.addJobChainLink(firstJob, secondJob)
    saveChain(firstJob,secondJob)
  }

  override def jobToBeExecuted(context: JobExecutionContext): Unit =
    super.jobToBeExecuted(context)
}

case class Chain(jobName:String,jobGroup:String,nextJobName:String,nextJobGroup:String)




