package cn.qs.consumer.bean;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wqs on 16/10/19.
 * 拆分sparkListenerEvent数据,形成自己需要的metric
 * 拆成 eventType(事件类型),dimension(主维度名),dimension2(从维度名),operatorType(计算类型)   dimensionValue(主维度值)  dimension2Value(从维度值)
 *
 */
public class SparkListenerEvent {


    private static Logger logger = LoggerFactory.getLogger(SparkListenerEvent.class);
    //拆成 eventType(事件类型),dimension(主维度名),dimension2(从维度名),operatorType(计算类型)   dimensionValue(主维度值)  dimension2Value(从维度值)

    /**
     * @param eventType 事件类型
     * @param dimension 主维度
     * @param dimension2 从维度
     * @param operatorType  计算类型
     * @param dimensionValue 辅维度值
     * @param value
     */
    public SparkListenerEvent(String eventType,String dimension,String dimension2,String operatorType,String dimensionValue,String value){
        this.eventType = eventType;
        this.dimension = dimension;
        this.dimension2 = dimension2;
        this.operatorType = operatorType;
        this.dimensionValue = dimensionValue;
        this.value = value;
    }

    private String dimensionValue;
    private String eventType;
    private String operatorType;
    private String value;
    private String filterCondition;

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public boolean isTrue(){


        return false;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    private String event;
    private String dimension;
    private String dimension2;
    private static String APPID = "AppId";
    private static String EVENTTYPE = "EventType";


    private static ObjectMapper mapper = new ObjectMapper();

    public String getDimensionValue() {
        return dimensionValue;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    public String toString(){
        return event;
    }


    public static List<SparkListenerEvent> buildSparkListenerEvent(String event){
        List<SparkListenerEvent> sparkListenerEvents = new ArrayList<SparkListenerEvent>();
        try {
            JsonNode jsonNode = mapper.readTree(event);
            List<StatisticsMetric> statisticsMetricses = StatisticsMetric.statisticsMetrics();
            for (StatisticsMetric statisticsMetric : statisticsMetricses) {
                String dimension = statisticsMetric.getDimension();
                String eventType = statisticsMetric.getEventType();
                String dimension2 = statisticsMetric.getDimension2();
                String operatorType = statisticsMetric.getOperatorType();
                if (jsonNode.findValue(EVENTTYPE).asText().equals(eventType)) {
                    String value = jsonNode.findValue(dimension2).asText();
                    String dimensionValue = jsonNode.findValue(dimension).asText();
                    SparkListenerEvent sparkListenerEvent = new SparkListenerEvent(eventType, dimension, dimension2, operatorType, dimensionValue, value);
                    sparkListenerEvents.add(sparkListenerEvent);
                }
            }
        }catch (Exception e){
            logger.error("jackson解析事件失败{}",e.getMessage());
        }
        return sparkListenerEvents;
    }


    public static void main(String[] args) throws Exception{

        String event = "{\"stageId\":0,\"stageAttemptId\":0,\"taskInfo\":{\"taskId\":2,\"index\":2,\"attemptNumber\":0,\"launchTime\":1477038021146,\"executorId\":\"driver\",\"host\":\"localhost\",\"taskLocality\":{\"name\":null},\"speculative\":false},\"appId\":\"local-1477038019655\",\"eventType\":\"SparkListenerTaskStart\",\"userName\":\"wqs\"}";

        ObjectMapper m = new ObjectMapper();
        JsonNode jsonNode = m.readTree(event);
        System.out.println(jsonNode.findValue("taskId").asInt());

    }
}
