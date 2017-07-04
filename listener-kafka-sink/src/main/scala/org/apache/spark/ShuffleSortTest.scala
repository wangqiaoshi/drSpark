package org.apache.spark

import java.io.File

import org.apache.spark.executor.TaskMetrics
import org.apache.spark.shuffle.{ IndexShuffleBlockResolver}
import org.apache.spark.shuffle.sort.{BypassMergeSortShuffleHandle, BypassMergeSortShuffleWriter}
import org.apache.spark.storage.BlockManager
import org.apache.spark.util.Utils

/**
  * Created by wqs on 16/10/30.
  */

//case class Demo(name:String,age:Int)
object ShuffleSortTest extends Logging{


  def main(args: Array[String]) {

//    log.error("{}",Demo("wangqiaoshi",18))
    println(System.getProperty("java.io.tmpdir"))

//    val conf = new SparkConf().setMaster("local[*]").setAppName("test")
//    val sc = new SparkContext(conf)
//    val tempDir = Utils.createTempDir()
//    val outputFile = File.createTempFile("shuffle", null, tempDir)
//    val taskMetrics = new TaskMetrics()
//
//    val rdd = sc.makeRDD(Seq((1,2),(3,4)))
//    var dependency: ShuffleDependency[Int, Int, Int] = new ShuffleDependency[Int,Int,Int](
//      rdd,
//     partitioner =  new HashPartitioner(7)
//    )
//    val shuffleHandle = new BypassMergeSortShuffleHandle[Int, Int](
//      shuffleId = 0,
//      numMaps = 2,
//      dependency = dependency
//    )
//
//
//    val taskContext = TaskContext.empty()
//    val blockManager:BlockManager = _
//    val blockResolver:IndexShuffleBlockResolver = _
//    val writer = new BypassMergeSortShuffleWriter[Int, Int]
//    (blockManager, blockResolver,
//      shuffleHandle,
//      0, // MapId
//      taskContext, conf)
  }


}
