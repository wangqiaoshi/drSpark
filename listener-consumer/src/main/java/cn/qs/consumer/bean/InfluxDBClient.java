package cn.qs.consumer.bean;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.squareup.okhttp.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.client.OkClient;
/**
 * Created by wqs on 16/10/21.
 */
public class InfluxDBClient {

    private static Logger logger = LoggerFactory.getLogger(InfluxDBClient.class);
    private String url;
    private String userName;
    private String password;
    private String dbName;
    private InfluxDB influxDB;

    public InfluxDBClient(String url, String userName, String  password, String dbName){
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.dbName = dbName;

    }

    public void initInfluxDB(){
        if(influxDB==null){
            OkHttpClient okclient = new OkHttpClient();
            okclient.setConnectTimeout(1000, TimeUnit.MILLISECONDS);
            okclient.setReadTimeout(200, TimeUnit.MILLISECONDS);
            okclient.setWriteTimeout(500, TimeUnit.MILLISECONDS);
            OkClient client = new OkClient(okclient);
            influxDB = InfluxDBFactory.connect(url, userName, password,client);
            influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
            logger.info("connected influxdb,{},{},{},{}",url,dbName,userName,password);

        }
    }


    /**
     *
     * @param tableName,influxdb measurement
     * @param fields
     * @param tags
     * @param policy
     */

    public void write(String tableName, Map<String,Object> fields,Map<String,String> tags,String policy){
        initInfluxDB();
        Point point = Point.measurement(tableName).tag(tags).fields(fields).build();
        influxDB.write(dbName,policy,point);
    }

}
