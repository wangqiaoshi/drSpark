package cn.qs.consumer.dao;

import cn.qs.consumer.bean.SparkListenerEvent;

import java.util.List;

/**
 * Created by wqs on 16/10/21.
 */
public interface SparkListenerEventDao {

    public void saveSparkListenerEvents(List<SparkListenerEvent> sparkListenerEventList);

}
