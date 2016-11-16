package cn.qs.metrics.util

import java.util.Timer

import org.apache.spark.{SparkConf, SparkEnv}

/**
  * Created by wqs on 16/11/10.
  */

object GroupByMetric{


  def apply(overTime: Int, period: Int,
            url: String, userName: String = "user", password: String = "pass"): GroupByMetric = 
    new GroupByMetric(overTime, period, url, userName, password)


  def apply(sparkConf:SparkConf): GroupByMetric = {

    val overTime = sparkConf.get("spark.metrics.dr.overTime","1800000").toInt
    val period = sparkConf.get("spark.metrics.dr.period","1200000").toInt
    val url = sparkConf.get("spark.metrics.dr.url","http://127.0.0.1:8086")
    val userName = sparkConf.get("spark.metrics.dr.userName","user")
    val password = sparkConf.get("spark.metrics.dr.pass","pass")
    new GroupByMetric(overTime, period, url, userName, password)
  }
 

}

class GroupByMetric(overTime:Int,period:Int,
                    url:String, userName:String="user", password:String="pass") extends Serializable{


  val keyStatusMap = new KeyStatusMap(overTime,period,url,userName,password)


  def register(key:String): Unit ={
    val keyStatus = new KeyStatus(key)
    keyStatusMap.put(key,keyStatus)
  }

  def mark(key:String): Unit ={
    keyStatusMap.finish(key)
  }



}





class KeyStatus(val key:String,beginTime:Long=System.currentTimeMillis(),var status:Boolean=false) extends Serializable{

  val appId =
    if(SparkEnv.get==null)"default"
    else  SparkEnv.get.conf.get("spark.app.id","default")

  var lastTime:Long =beginTime
  def interval(): Long ={
    lastTime-beginTime
  }
}



