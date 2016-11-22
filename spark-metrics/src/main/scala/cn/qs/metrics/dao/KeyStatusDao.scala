package cn.qs.metrics.dao

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient.Builder
import cn.qs.metrics.util.KeyStatus
import org.apache.spark.Logging
import org.influxdb.InfluxDB.ConsistencyLevel
import org.influxdb.{InfluxDB, InfluxDBFactory}
import org.influxdb.dto.{BatchPoints, Point}

import scala.collection.mutable
/**
  * Created by wqs on 16/11/13.
  */
trait KeyStatusDao extends Logging{


  def save(keyStatusList: mutable.MutableList[KeyStatus] )

}

class KeyStatusInfluxdbDao(url:String,userName:String,password:String) extends KeyStatusDao{
  val builder = new Builder()
  builder.readTimeout(2000,TimeUnit.MILLISECONDS)
  builder.writeTimeout(3000,TimeUnit.MILLISECONDS)
  var influxDB:InfluxDB = _
  val dbName = "sparkMetrics"
  log.info("connected to influxdb,{},{},{}",Seq(url,userName,password):_*)


  def getConn(): InfluxDB ={
    if(influxDB==null){
      influxDB =  InfluxDBFactory.connect(url, userName, password, builder)
    }
    influxDB
  }


  Runtime.getRuntime.addShutdownHook(new Thread(){
    override def run(): Unit = {
      if(influxDB!=null){
        influxDB.disableBatch()
      }
    }
  })

  override def save(keyStatusList: mutable.MutableList[KeyStatus]): Unit = {
    try {
      val batchPoints = BatchPoints.database(dbName)
        .consistency(ConsistencyLevel.ONE)
        .build()

      for (keyStatus <- keyStatusList) {
        val point = Point.measurement("groupByKeyStatus")
          .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
          .tag("appId", keyStatus.appId)
          .tag("status",keyStatus.status.toString)
          .addField("groupkey", keyStatus.key)
          .addField("period", keyStatus.interval())
          .build()
        batchPoints.point(point)
      }
      getConn.write(batchPoints)
    }catch {
      case e:Exception=>
        log.info("save failed:{}",e.getMessage)
    }
  }
}

object KeyStatusInfluxdbDao{
  def main(args: Array[String]) {

    val keyStatusInfluxdbDao = new KeyStatusInfluxdbDao("http://192.168.6.52:8086","user","pass")
    val list = new mutable.MutableList[KeyStatus]
    list.+=(new KeyStatus("wang"))
    keyStatusInfluxdbDao.save(list)
  }
}

