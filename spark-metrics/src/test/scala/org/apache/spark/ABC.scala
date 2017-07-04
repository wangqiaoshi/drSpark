package org.apache.spark

/**
  * Created by wqs on 16/11/22.
  */

import org.apache.spark.deploy.rest.RestSubmissionClient
class ABC {

}

object ABC{


  val client = new RestSubmissionClient("mesos://192.168.6.52:7077")

  def main(args: Array[String]) {

    client.killSubmission("driver-20161122112640-0001")
  }

}
