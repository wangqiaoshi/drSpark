package cn.qs.metrics.util


import scala.collection.mutable
import java.util.TimerTask
import java.util.Timer

import cn.qs.metrics.dao.KeyStatusInfluxdbDao
import org.apache.spark.Logging

/**
  * Created by wqs on 16/11/12.
  *
  * 凡是GroupByKey 执行超过overTime,不管执行完成还是没有完成都进行报告,报告的时候首先将已经完成的剔除
  *
  * keyStatusMap维持着
  */

object  KeyStatusMap {
   val hashMap = new mutable.HashMap[String,KeyStatus]()
}
import KeyStatusMap._
class KeyStatusMap(overTime:Int,period:Int,
                   url:String, userName:String="user", password:String="pass")
  extends Serializable with Logging{

  val timer = new Timer("timer",true)
  timer.schedule(new MapTimerTask(), 100,period)


  /**
    * @param key
    * @param keyStatus
    */
  def put(key:String,keyStatus: KeyStatus): Unit = synchronized {
    hashMap.put(key, keyStatus)
  }


  /**
    *
    * 标识keystaus已经完成,如果小于overTime,就直接移除
    * @param key
    */
  def finish(key:String): Unit = synchronized{
    KeyStatusMap.hashMap.get(key) match {
      case Some(keyStatus)=>
        keyStatus.lastTime = System.currentTimeMillis()
        if(keyStatus.interval()<overTime) hashMap.remove(key)
        else keyStatus.status=true
      case None=>
    }
  }



  /**
    * map定时器,将运行时间超过overTime,发送报告
    */
  class MapTimerTask extends TimerTask with Serializable{

    val keyStatusInfluxdbDao = new KeyStatusInfluxdbDao(url,userName,password)
    override def run(): Unit = {

      val list = getOvertime()
      keyStatusInfluxdbDao.save(list)
    }

    /**
      * 获取超过overtime的keystatus
      * @return
      */
    def getOvertime(): mutable.MutableList[KeyStatus] ={

      val overList = new mutable.MutableList[KeyStatus]()
      hashMap.foreach{case (key:String,keyStatus:KeyStatus)=>{

        if(keyStatus.interval()>= overTime){
          overList.+=(keyStatus)
          if(keyStatus.status)hashMap.remove(key)
        }
        else {
          keyStatus.lastTime = System.currentTimeMillis()
        }
      }}
      overList
    }
  }
}



