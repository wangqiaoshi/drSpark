package org.apache.spark

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.apache.spark.scheduler.{SparkListenerTaskEnd, SparkListenerTaskStart, SparkListenerUnpersistRDD, _}
import org.apache.spark.util.{JsonProtocol, Utils}
import java.util.Properties

import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import org.json4s.JsonAST.JObject
import org.json4s.DefaultFormats


/**
  * Created by wqs on 16/10/15.
  */
class KafkaListener extends SparkListener with Logging{

  private implicit val format = DefaultFormats
  private[this] var _ruleProperties:Properties = _

  private[this] def ruleProperties(): Properties ={
    if(_ruleProperties==null){
      _ruleProperties = new Properties()
      val inputStream = getClass.getClassLoader.getResourceAsStream("sink-default.properties")
      _ruleProperties.load(inputStream)
    }
    _ruleProperties
  }


  val brokers = ruleProperties.getProperty("sink.kafka.metadata.broker.list")
  val topic = ruleProperties().getProperty("sink.kafka.topic")
  val props = new Properties()
  props.put("metadata.broker.list", brokers)
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  val kafkaConfig = new ProducerConfig(props)
  val producer = new Producer[String, String](kafkaConfig)



  override def onStageCompleted(stageCompleted: SparkListenerStageCompleted): Unit = {

    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(stageCompleted)
    val jvResult = JsonProtocol.stageCompletedToJson(stageCompleted)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)
//    val jvResult = (Extraction.decompose(stageCompleted))
//      .asInstanceOf[JObject] ~ ("appId"->appId) ~
//      ("eventType"->eventType) ~
//      ("userName"->userName)

    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)


  }

  override def onStageSubmitted(stageSubmitted: SparkListenerStageSubmitted): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(stageSubmitted)
    val jvResult = JsonProtocol.stageSubmittedToJson(stageSubmitted)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)

  }

  override def onTaskStart(taskStart: SparkListenerTaskStart): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(taskStart)
    val jvResult = JsonProtocol.taskStartToJson(taskStart)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)
    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onTaskGettingResult(taskGettingResult: SparkListenerTaskGettingResult): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(taskGettingResult)
    implicit val formats = Serialization.formats(NoTypeHints)
    val jvResult = JsonProtocol.taskGettingResultToJson(taskGettingResult)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(taskEnd)
    implicit val formats = Serialization.formats(NoTypeHints)
    val jvResult =  JsonProtocol.taskEndToJson(taskEnd)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onJobStart(jobStart: SparkListenerJobStart): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(jobStart)
    val jvResult = JsonProtocol.jobStartToJson(jobStart)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onJobEnd(jobEnd: SparkListenerJobEnd): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(jobEnd)
    val jvResult = JsonProtocol.jobEndToJson(jobEnd)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onEnvironmentUpdate(environmentUpdate: SparkListenerEnvironmentUpdate): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(environmentUpdate)
    implicit val formats = Serialization.formats(NoTypeHints)
    val jvResult = JsonProtocol.environmentUpdateToJson(environmentUpdate)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onBlockManagerAdded(blockManagerAdded: SparkListenerBlockManagerAdded): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(blockManagerAdded)
    val jvResult = JsonProtocol.blockManagerAddedToJson(blockManagerAdded)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onBlockManagerRemoved(blockManagerRemoved: SparkListenerBlockManagerRemoved): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(blockManagerRemoved)
    val jvResult = JsonProtocol.blockManagerRemovedToJson(blockManagerRemoved)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onUnpersistRDD(unpersistRDD: SparkListenerUnpersistRDD): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(unpersistRDD)
    val jvResult = JsonProtocol.unpersistRDDToJson(unpersistRDD)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onApplicationStart(applicationStart: SparkListenerApplicationStart): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(applicationStart)
    implicit val formats = Serialization.formats(NoTypeHints)
    val jvResult = JsonProtocol.applicationStartToJson(applicationStart)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult)
    send(strResult)
  }

  override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(applicationEnd)
    implicit val formats = Serialization.formats(NoTypeHints)
    val jvResult = JsonProtocol.applicationEndToJson(applicationEnd)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult)
    send(strResult)
  }

  override def onExecutorMetricsUpdate(executorMetricsUpdate: SparkListenerExecutorMetricsUpdate): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(executorMetricsUpdate)
    val jvResult = JsonProtocol.executorMetricsUpdateToJson(executorMetricsUpdate)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)

    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onExecutorAdded(executorAdded: SparkListenerExecutorAdded): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(executorAdded)
    val jvResult = JsonProtocol.executorAddedToJson(executorAdded)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onExecutorRemoved(executorRemoved: SparkListenerExecutorRemoved): Unit = {
    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(executorRemoved)
    val jvResult = JsonProtocol.executorRemovedToJson(executorRemoved)
      .asInstanceOf[JObject] ~ ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)


    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }

  override def onBlockUpdated(blockUpdated: SparkListenerBlockUpdated): Unit = {

    val appId = SparkEnv.get.conf.get("spark.app.id",null)
    val userName = Utils.getCurrentUserName()
    val eventType = Utils.getFormattedClassName(blockUpdated)
    val jvResult = ("BlockIdName" -> blockUpdated.blockUpdatedInfo.blockId.name) ~
      ("BlockIdIsRDD" -> blockUpdated.blockUpdatedInfo.blockId.isRDD) ~
      ("BlockIdIsShuffle" -> blockUpdated.blockUpdatedInfo.blockId.isShuffle) ~
      ("DiskSize" -> blockUpdated.blockUpdatedInfo.diskSize) ~
      ("ExecutorId" -> blockUpdated.blockUpdatedInfo.blockManagerId.executorId) ~
      ("host"->blockUpdated.blockUpdatedInfo.blockManagerId.host) ~
      ("blockManagerId"->blockUpdated.blockUpdatedInfo.blockManagerId.port) ~
      ("externalBlockStoreSize"->blockUpdated.blockUpdatedInfo.externalBlockStoreSize) ~
      ("MemSize"->blockUpdated.blockUpdatedInfo.memSize)~
      ("UseDisk"->blockUpdated.blockUpdatedInfo.storageLevel.useDisk) ~
      ("UseMemory"->blockUpdated.blockUpdatedInfo.storageLevel.useMemory) ~
      ("UseMemory"->blockUpdated.blockUpdatedInfo.storageLevel.useOffHeap) ~
      ("AppId"->appId) ~
      ("EventType"->eventType) ~
      ("UserName"->userName)
    val strResult = write(jvResult).replaceAll(" ","")
    send(strResult)
  }
  def send(message:String): Unit ={
    try {
      producer.send(new KeyedMessage[String, String](topic, message, message))
    }
    catch {
      case e:Exception=>
        log.error("发送kafka失败!")
    }
  }


}

object KafkaListener{


  def main(args: Array[String]) {

    println(Utils.resolveURI("hdfs://tdhdfs/usr/install/tongdun/app").getScheme)
  }
}
