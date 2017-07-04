package cn.qs.consumer.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wqs on 16/11/1.
 */
public class Config {

    public static String configPath  = System.getProperty("user.dir");
    private static String fileName = "config.properties";
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    private static Properties configProperties =  null;

    public static Properties getConfigProperties() {
        try {
            if (configProperties == null) {
                configProperties = new Properties();
                InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(configPath+fileName);
                if(inputStream==null)
                    inputStream = Config.class.getClassLoader().getResourceAsStream(fileName);
                configProperties.load(inputStream);
            }
        }catch (Exception e){
            logger.error("获取properties失败{}",e.getMessage());
        }

      return configProperties;
    }


    public static String kafkaZkList(){
        return getConfigProperties().getProperty("kafka.zk.list");
    }

    public static String kafkaZkComsumer(){
        return getConfigProperties().getProperty("kafka.zk.consumer");
    }


    public static String kafkaZkTopic(){
        return getConfigProperties().getProperty("kafka.zk.topic");
    }

    public static String kafkaZkThreadNum(){
        return getConfigProperties().getProperty("kafka.zk.threadNum");
    }

    public static String influxdbUrl(){
        return getConfigProperties().getProperty("influxdb.url");
    }

    public static String influxdbName(){
        return getConfigProperties().getProperty("influxdb.dbName");
    }

    public static String influxdbUserName(){
        return getConfigProperties().getProperty("influxdb.userName");
    }

    public static String influxdbPassword(){
        return getConfigProperties().getProperty("influxdb.password");
    }

    public static void main(String[] args) {

        System.out.println(Config.kafkaZkTopic());
    }

}
