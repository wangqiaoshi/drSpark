/**
  * Created by wqs on 16/11/11.
  */

import java.util.Comparator

import cn.qs.metrics.util.GroupByMetric
import com.google.common.collect.TreeMultiset
import org.apache.spark.{SparkConf, SparkContext}
object Test {

  def main(args: Array[String]) {




    val conf = new SparkConf().setMaster("local").setAppName("demo")
    val sparkContext = new SparkContext(conf)
    val  groupByMetric = GroupByMetric(1000,500,"http://192.168.6.52:8086")
    sparkContext.makeRDD(Seq(("wang",18),("zhang",18),("sian",19),("hen",20)))
      .groupByKey().map(kv=>{
      groupByMetric.register(kv._1)
      Thread.sleep(7000)
      groupByMetric.mark(kv._1)
      kv._1
    }).count


  }






}

class MyComparable extends  Comparator[String]{
  override def compare(o1: String, o2: String): Int = {
    o1.length-o2.length
  }
}
