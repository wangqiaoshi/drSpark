package cn.qs.consumer.bean;

import cn.qs.consumer.dao.StatisticsMetricDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wqs on 16/10/18.
 */
public class StatisticsMetric {



    public static Logger logger = LoggerFactory.getLogger(StatisticsMetric.class);
    private long id;
    private String name;
    private String metric;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    private JsonNode metricJson;
    private ObjectMapper om = new ObjectMapper();
    public JsonNode getMetricJsonObject() throws Exception{
        if(metricJson == null){

            metricJson =  om.readTree(metric);
        }
        return metricJson;
    }

    private String DIMENSION = "dimension";
    private String DIMENSION2 = "dimension2";
    private String EVENTTYPE = "eventType";
    private String OPERATORTORTYPE = "operatorType";


    public String getDimension() {
        return get(DIMENSION);
    }

    public String getDimension2(){
        return get(DIMENSION2);
    }

    public String getEventType(){
        return get(EVENTTYPE);
    }

    public String getOperatorType(){
        return get(OPERATORTORTYPE);
    }




    public String get(String colName){
        String dimension = null;
        try {
            dimension = getMetricJsonObject().get(colName).asText();
        }catch (Exception e){
            logger.error("获取"+colName+"失败");
        }
        return  dimension;
    }


    private static StatisticsMetricDao statisticsMetricsDao = new StatisticsMetricDao();

    private static List<StatisticsMetric> _statisticsMetrics = null;


    public static List<StatisticsMetric> statisticsMetrics(){
        if(_statisticsMetrics == null){
            _statisticsMetrics = statisticsMetricsDao.getAllStatisticsMetrics();
        }
        return _statisticsMetrics;
    }
    static Timer timer = new Timer();
    static {
        MetricsTask metricsTask = new MetricsTask();
        timer.schedule(metricsTask, 10, 60000);
    }

    static class MetricsTask extends TimerTask {
        @Override
        public void run() {
            try {
                _statisticsMetrics = statisticsMetricsDao.getAllStatisticsMetrics();
                logger.info("定时获取指标成功!,statisticsMetrics size:{}",_statisticsMetrics.size());
            }catch (Exception e){
                logger.error("定时更新指标失败{}",e.getMessage());
            }
        }
    }


}
