package org.apache.spark

import org.quartz.{Job, JobExecutionContext}

import scala.io.Source


/**
  * Created by wqs on 16/12/9.
  */
class JavaJob extends Job{


  override def execute(context: JobExecutionContext): Unit = {

    val jobDataMap = context.getMergedJobDataMap
    val params = jobDataMap.getString("params")
    val classPath = jobDataMap.getString("classpath")
    val javaClass = jobDataMap.getString("class")

    val commandLine = getCommandLine(params,classPath,javaClass)
    run(commandLine)

  }



  def  getCommandLine(params:String,classPath:String,javaClass:String): String ={

    val strBuf = new StringBuffer()
    strBuf.append("java -cp ").append(classPath).append(" ").append(javaClass).append(" ").append(params)

    strBuf.toString
  }


  def run(command:String): Unit ={

    val process = Runtime.getRuntime.exec(command)
    val inputStream = process.getInputStream
    val errStream = process.getErrorStream
    val bufferSource = Source.fromInputStream(inputStream)
    val stderr = Source.fromInputStream(errStream)
    bufferSource.getLines().foreach(line=>println(line))
    stderr.getLines().foreach(line=>println(line))
  }


}
