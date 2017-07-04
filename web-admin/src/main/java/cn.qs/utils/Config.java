package cn.qs.utils;

import cn.qs.dao.InfluxdbDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wqs on 16/11/3.
 */
public class Config {

    static Logger logger = LoggerFactory.getLogger(Config.class);

    private static Properties configProperties =  null;

    public static Properties getConfigProperties() {
        try {
            if (configProperties == null) {
                configProperties = new Properties();
                InputStream inputStream = InfluxdbDao.class.getClassLoader().getResourceAsStream("config.properties");
                configProperties.load(inputStream);
            }
        }catch (Exception e){
            logger.error("获取properties失败{}",e.getMessage());
        }
        return configProperties;
    }

   public static String getUrl(){
        return getConfigProperties().getProperty("influxdb.url");
    }

    public static String getUserName(){
        return getConfigProperties().getProperty("influxdb.user");
    }

    public static String getPassword(){
        return getConfigProperties().getProperty("influxdb.password");
    }


    public static String getGrafanaUrl(){
        return getConfigProperties().getProperty("grafana.url");
    }



}
