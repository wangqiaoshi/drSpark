package cn.qs.consumer.dao;

import cn.qs.consumer.bean.Config;
import cn.qs.consumer.bean.InfluxDBClient;
import cn.qs.consumer.bean.SparkListenerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wqs on 16/10/21.
 *
 * influxdb measurement Name的设计,eventType.dimension2.operatorType
 * tags ->  dimension,dimensionValue , field ->  value
 *
 */
public class SparkListenerEventInfluxDbDao implements SparkListenerEventDao {

    private String url= Config.influxdbUrl();
    private String dbName = Config.influxdbName();
    private String userName = Config.influxdbUserName();
    private String password = Config.influxdbPassword();
    private static Logger logger = LoggerFactory.getLogger(SparkListenerEventInfluxDbDao.class);
    private InfluxDBClient client = new InfluxDBClient(url,userName,password,dbName);


    /**
     * 获取influxdb 的 tags,
     * tags -> dimensionValue
     * @param sparkListenerEvent
     * @return
     */
    Map<String,String> getTags(SparkListenerEvent sparkListenerEvent){

        String dimensionValue = sparkListenerEvent.getDimensionValue();
        Map<String,String> tags = new HashMap<String, String>();
        tags.put("dimensionValue",dimensionValue);
        return tags;
    }

    /**
     *
     * @param sparkListenerEvent
     * @return
     */
    Map<String,Object> getField(SparkListenerEvent sparkListenerEvent){
        String value = sparkListenerEvent.getValue();
        String  operatorType = sparkListenerEvent.getOperatorType();

        Map<String,Object> field = new HashMap<String,Object>();
        switch(operatorType){
            case "sum": {
                float sum = Float.parseFloat(value);
                field.put("value", sum);
                break;
            }
            case "std": {
                float sum = Float.parseFloat(value);
                field.put("value", sum);
                break;
            }

            case "count":{
                field.put("value",1);
                break;
            }
            default:
                field.put("value",value);


        }
        return field;
    }

    /**
     * measurement name由eventType.dimension2.operatorType组成
     * @param sparkListenerEvent
     * @return measurement name
     */

    private String getMeasurementName(SparkListenerEvent sparkListenerEvent){
     String eventType = sparkListenerEvent.getEventType();
     String dimension = sparkListenerEvent.getDimension();
     String dimension2 = sparkListenerEvent.getDimension2();
     String operatorType = sparkListenerEvent.getOperatorType();
     return eventType+"."+dimension+"."+dimension2+"."+operatorType;
    }


    public void saveSparkListenerEvents(List<SparkListenerEvent> sparkListenerEventList) {


        for(SparkListenerEvent sparkListenerEvent:sparkListenerEventList) {
            String measurementName =   getMeasurementName(sparkListenerEvent);
            Map<String,Object> field = getField(sparkListenerEvent);
            Map<String,String> tags = getTags(sparkListenerEvent);
            logger.info("write {}",measurementName);
            client.write(measurementName,field,tags,"autogen");
        }


    }


    public static void main(String[] args) {
        SparkListenerEventInfluxDbDao influxDbDao = new SparkListenerEventInfluxDbDao();
        String event = "{\"userName\":\"wqs\",\"totalCores\":15,\"eventType\":\"eventType\"}";
        List<SparkListenerEvent> sparkListenerEventList = SparkListenerEvent.buildSparkListenerEvent(event);
        influxDbDao.saveSparkListenerEvents(sparkListenerEventList);
    }










}
