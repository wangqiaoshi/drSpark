package cn.qs.metrics.dao

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient.Builder
import cn.qs.metrics.util.KeyStatus
import org.apache.spark.Logging
import org.influxdb.InfluxDB.ConsistencyLevel
import org.influxdb.InfluxDBFactory
import org.influxdb.dto.{BatchPoints, Point}

import scala.collection.mutable
/**
  * Created by wqs on 16/11/13.
  */
trait KeyStatusDao {

  def save(keyStatus: KeyStatus)

  def save(keyStatusList: mutable.MutableList[KeyStatus] )


}

class KeyStatusInfluxdbDao(url:String,userName:String,password:String) extends KeyStatusDao with Logging{
  val builder = new Builder()
  builder.readTimeout(2000,TimeUnit.MILLISECONDS)
  builder.writeTimeout(2000,TimeUnit.MILLISECONDS)
  val influxDB = InfluxDBFactory.connect(url, userName, password, builder)
  val dbName = "sparkMetrics"
  log.info("connected to influxdb,{},{},{}",Seq(url,userName,password):_*)


  override def save(keyStatus: KeyStatus): Unit = {



  }

  override def save(keyStatusList: mutable.MutableList[KeyStatus]): Unit = {

    val batchPoints = BatchPoints.database(dbName)
//      .retentionPolicy("autogen")
      .consistency(ConsistencyLevel.ONE)
      .build()

    for(keyStatus<-keyStatusList){
      val point = Point.measurement("groupByKeyStatus")
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        .tag("appId",keyStatus.appId)
        .tag("groupkey", keyStatus.key)
        .addField("period", keyStatus.interval())
        .build()
      batchPoints.point(point)
    }


    influxDB.write(batchPoints)
//    batchPoints.getPoints.clear()

  }
}

