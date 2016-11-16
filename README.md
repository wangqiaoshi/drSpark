#drSpark
#介绍
drSpark是一个基于spark的智能优化平台.

#功能
* 监控,针对应用程序,提交用户
* 数据发现,比如发现那个数据使用的比较频繁
* 诊断,提供优化建议


#组成
###收集器

* SparkListener
* Spark metrics
* drSpark metrics
* spark,hdfs logback

###发送器

* drSpark producer

###落地存储

* drSpark comsumer
