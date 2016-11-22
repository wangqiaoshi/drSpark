/**
  * Created by wqs on 16/11/16.
  */

import java.util.concurrent.{Executor, Executors, ThreadFactory, TimeUnit}

import org.apache.spark.{SparkConf, SparkContext}
import org.influxdb.InfluxDB.ConsistencyLevel
import org.influxdb.InfluxDBFactory
import org.influxdb.dto.BatchPoints
import org.influxdb.dto.Point

import scala.collection.mutable
import scala.util.Random
class QuoteTest {


  def put(hash:mutable.HashMap[String,String]): Unit ={
    hash.put("wang","25")
    println(System.identityHashCode(hash))

  }



}


class A{

  val  influxDB = InfluxDBFactory.connect("http://192.168.6.52:8086", "user", "pass");
  influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS)

  val batchPoints = BatchPoints
    .database("sparkMetrics")
    .tag("async", "true")
    .retentionPolicy("autogen")
    .consistency(ConsistencyLevel.ALL)
    .build()
  val point1 = Point.measurement("cpu")
    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    .addField("idle", 90L)
    .addField("user", 9L)
    .addField("system", 1L)
    .build()
  val point2 = Point.measurement("disk")
    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    .addField("used", 80L)
    .addField("free", 1L)
    .build()
  batchPoints.point(point1)
  batchPoints.point(point2)
  influxDB.disableBatch()
  var flag = false
  val map = new mutable.HashMap[String,String]()
  Runtime.getRuntime.addShutdownHook(new Thread(){
    override def run(): Unit = {
      flag = true
      influxDB.disableBatch()
      println("xx")
    }
  })
}

class MyThreadFactory extends ThreadFactory{
  override def newThread(r: Runnable): Thread = {
    val t = new Thread(r,"t1")
    t.setDaemon(true)
    t
  }
}

class R extends Runnable{

  override def run(): Unit = {
    println("xxxx")
  }
}


object QuoteTest{


  def main(args: Array[String]) {
//    val schedule = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory)
//    schedule.scheduleAtFixedRate(new R,1000,100,TimeUnit.MILLISECONDS)
//    Thread.sleep(10000)
//    val map = new mutable.HashMap[String,String]()
//    val  test = new QuoteTest()
//    val a = new A()
//    println(System.identityHashCode(a.map))
//    test.put(a.map)
//    put(map)
//    val t = new Thread(){
//    override def run(): Unit = {
//     Thread.sleep(1000000000)
//    }
//    }
//    t.setDaemon(false)
//    t.start()
//    val a = new A()

    //("jing",10),("lou",12)
//    ("zhang",20),("zhang",11),("zhang",21),("zhang",12),("zhang",12),

    val sc = new SparkConf().setAppName("demo").setMaster("local[1]")
    val sparkContext =new SparkContext(sc)
    val rdd =  sparkContext.makeRDD(Seq(
      ("wang",25),("wang",26),("wang",18),("wang",15),("wang",7),("wang",1)
      ))
        .groupByKey().flatMap(kv=>{

      var i =0
      kv._2.toIterator.map(r=>{

        i=i+1
        println(r)
        r
      })


    })

    sparkContext.runJob(rdd,add _)


    def add(list:Iterator[Int]): Unit ={

      var i=0
      val items = new mutable.MutableList[Int]()
      while(list.hasNext){
        items.+=(list.next())
        if(i>=2){
          println(items.mkString(","))
          items.clear()
          i=0
        }else if(!list.hasNext){
          println(items.mkString(","))
        }
        i=i+1
      }

    }
//
//    val  list = new mutable.MutableList[Int]()
//
//    for(i<- 0 to 10000000){
//      list.+=(Random.nextInt(1000))
//    }
//    val timestamp = System.currentTimeMillis()
//    list.toIterator
//    val endTime = System.currentTimeMillis()
//    println(endTime-timestamp)


//    val ls= List("a","b","c","d")
//    val mus = new mutable.MutableList[String]
//    println(ls.map(r=>{
//      mus.+=(r)
//    }).mkString(","))









  }
  def put(hash:mutable.HashMap[String,String]): Unit ={
    hash.put("wang","25")
    println(System.identityHashCode(hash))

  }




}
