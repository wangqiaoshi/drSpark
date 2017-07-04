package cn.qs.consumer.bean;

import cn.qs.consumer.dao.SparkListenerEventDao;
import cn.qs.consumer.dao.SparkListenerEventInfluxDbDao;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by wqs on 16/10/17.
 */
public class ConsumerRunable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ConsumerRunable.class);
    private KafkaStream m_stream;
    private int m_threadNumber;
    private SparkListenerEventDao sparkListenerEventDao = new SparkListenerEventInfluxDbDao();

    public ConsumerRunable(KafkaStream a_stream, int a_threadNumber) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
    }

    public void run() {

        try {
            ConsumerIterator<byte[], byte[]> it = m_stream.iterator();

            while (it.hasNext()) {
                String message = new String(it.next().message());
                List<SparkListenerEvent> sparkListenerEvents = SparkListenerEvent.buildSparkListenerEvent(message);
                sparkListenerEventDao.saveSparkListenerEvents(sparkListenerEvents);
            }
        }catch (Exception e){
            logger.error("线程运行失败{}",e.getMessage());
        }
    }
}
