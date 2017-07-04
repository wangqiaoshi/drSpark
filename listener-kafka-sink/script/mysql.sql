CREATE DATABASE drSpark;

CREATE TABLE `dr_statistics_metrics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `metric` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

grant all privileges on drSpark.* to drSpark@"%" identified  by 'drSpark';
insert into dr_statistics_metrics(name,metric) value('SparkListenerApplicationStart.AppId.AppId.set','{"dimension":"AppId","dimension2":"AppId","eventType":"SparkListenerApplicationStart","operatorType": "set"}');
insert into dr_statistics_metrics(name,metric) value('SparkListenerExecutorAdded.UserName.TotalCores.sum','{"dimension":"UserName","dimension2":"TotalCores","eventType":"SparkListenerExecutorAdded","operatorType": "sum"}');
insert into dr_statistics_metrics(name,metric) value('SparkListenerBlockManagerAdded.UserName.MaximumMemory','{"dimension":"UserName","dimension2":"MaximumMemory","eventType":"SparkListenerBlockManagerAdded","operatorType": "sum"}');
insert into dr_statistics_metrics(name,metric) value('SparkListenerApplicationStart.UserName.UserName','{"dimension":"UserName","dimension2":"UserName","eventType":"SparkListenerApplicationStart","operatorType": "count"}');