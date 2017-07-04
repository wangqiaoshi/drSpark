package cn.qs.dao;

import cn.qs.bean.*;
import cn.qs.utils.Config;
import com.squareup.okhttp.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.client.OkClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by wqs on 16/11/3.
 */
public class InfluxdbDao {

    private static Logger logger = LoggerFactory.getLogger(InfluxdbDao.class);

    private static InfluxDB influxDB;
    private static String url = Config.getUrl();
    private static String userName = Config.getUserName();
    private static String password = Config.getPassword();

    static {
        initInfluxDB();
    }


    public static void initInfluxDB(){
        if(influxDB==null) {
            OkHttpClient okclient = new OkHttpClient();
            okclient.setConnectTimeout(1000, TimeUnit.MILLISECONDS);
            okclient.setReadTimeout(800, TimeUnit.MILLISECONDS);
            okclient.setWriteTimeout(500, TimeUnit.MILLISECONDS);
            OkClient client = new OkClient(okclient);
            influxDB = InfluxDBFactory.connect(url, userName, password, client);
            influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
        }

    }




    public static QueryResult query(String sql){


        Query query = new Query(sql, "drSpark");
        initInfluxDB();
        QueryResult result = influxDB.query(query);
        return result;
    }


    public static List<String> queryAppId(){
        QueryResult queryResult = query("select value from \"SparkListenerApplicationStart.AppId.AppId.set\" order by time desc limit 50");
        List<QueryResult.Result> results =  queryResult.getResults();
        List<String> appList = new ArrayList<String>();
        for(QueryResult.Result result:results){
            List<QueryResult.Series> serriesList = result.getSeries();
            if(serriesList!=null)
            for(QueryResult.Series series:result.getSeries()) {
                for (List<Object> values : series.getValues()) {
                    appList.add(values.get(1).toString());
                }
            }
        }
        return appList;
    }

    public static List<TotalCore> queryTotalCore(){
        QueryResult queryResult = query("select sum(value) from \"SparkListenerExecutorAdded.UserName.TotalCores.sum\" where time > now() - 1d group by dimensionValue");
        List<QueryResult.Result> results =  queryResult.getResults();
        List<TotalCore> totalCores = new ArrayList<TotalCore>();
        double sum = 0;
        for(QueryResult.Result result:results){
            List<QueryResult.Series> seriesList = result.getSeries();
            if(seriesList!=null)
            for(QueryResult.Series series: seriesList){
               String userName =  series.getTags().get("dimensionValue");
               double core = (Double)series.getValues().get(0).get(1);
                sum = sum+core;
               TotalCore totalCore = new TotalCore();
               totalCore.setName(userName);
               totalCore.setCores(core);
               totalCores.add(totalCore);
            }
        }
        for(TotalCore totalCore:totalCores){
            totalCore.setY(totalCore.getCores()/sum);
        }
        return totalCores;
    }


    public static List<Memory> queryMemory(){
        List<Memory> memories = new ArrayList<Memory>();
        QueryResult queryResult = query("select sum(value) from \"SparkListenerExecutorAdded.UserName.TotalCores.sum\" where time > now() - 1d group by dimensionValue");
        List<QueryResult.Result> results =  queryResult.getResults();
        double sum = 0;
        for(QueryResult.Result result:results){
            List<QueryResult.Series> seriesList = result.getSeries();
            if(seriesList!=null)
                for(QueryResult.Series series: seriesList){
                    String userName =  series.getTags().get("dimensionValue");
                    double memorySize = (Double)series.getValues().get(0).get(1);
                    sum = sum+memorySize;
                    Memory memory = new Memory(userName,memorySize);

                    memories.add(memory);
                }
        }
        for(Memory memory:memories){
            memory.setY(memory.getMemorySize()/sum);
        }
        return memories;
    }




    public  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");



    public static List<SubmitMemory> querySubmitMomery(){
        List<SubmitMemory> submitCounts = new ArrayList<SubmitMemory>();
        QueryResult queryResult = query("select  sum(value)/(1024*2014)  from \"SparkListenerBlockManagerAdded.UserName.MaximumMemory.sum\" where time > now() - 1d group by dimensionValue,time(1h)");
        List<QueryResult.Result> results =  queryResult.getResults();

        try {
            for (QueryResult.Result result : results) {
                for (QueryResult.Series series : result.getSeries()) {
                    Map<String, String> tags = series.getTags();
                    String userName = tags.get("dimensionValue");
                    SubmitMemory submitMemory = new SubmitMemory(userName);
                    for(List<Object> values:series.getValues()){
                        Long timestamp = sdf.parse(values.get(0).toString()).getTime();
                        float count = 0;
                        if(values.get(1)!=null) {
                            count = Float.parseFloat(values.get(1).toString());
                        }
                        submitMemory.addPoint(timestamp,count);
                    }
                    submitCounts.add(submitMemory);
                }
            }
        }catch (Exception e){
            logger.error("查询次数统计失败:{}",e.getMessage());
        }
        return submitCounts;
    }

    public static List<SubmitCount> querySubmitCount(){
        List<SubmitCount> submitCounts = new ArrayList<SubmitCount>();
        QueryResult queryResult = query("select  sum(value)  from \"SparkListenerApplicationStart.UserName.UserName.count\" where time > now() - 1d group by dimensionValue,time(1h)");
        List<QueryResult.Result> results =  queryResult.getResults();

        try {
            for (QueryResult.Result result : results) {
                for (QueryResult.Series series : result.getSeries()) {
                    Map<String, String> tags = series.getTags();
                    String userName = tags.get("dimensionValue");
                    SubmitCount submitCount = new SubmitCount(userName);
                    for(List<Object> values:series.getValues()){
                        Long timestamp = sdf.parse(values.get(0).toString()).getTime();
                        float count = 0;
                        if(values.get(1)!=null) {
                            count = Float.parseFloat(values.get(1).toString());
                        }
                        submitCount.addPoint(timestamp,count);
                    }
                    submitCounts.add(submitCount);
                }
            }
        }catch (Exception e){
            logger.error("查询次数统计失败:{}",e.getMessage());
        }
        return submitCounts;
    }


    public static List<SubmitCore> querySubmitCore(){
        List<SubmitCore> submitCores = new ArrayList<SubmitCore>();
        QueryResult queryResult = query("select  sum(value)  from \"SparkListenerExecutorAdded.UserName.TotalCores.sum\" where time > now() - 1d group by dimensionValue,time(1h)");
        List<QueryResult.Result> results =  queryResult.getResults();

        try {
            for (QueryResult.Result result : results) {
                for (QueryResult.Series series : result.getSeries()) {
                    Map<String, String> tags = series.getTags();
                    String userName = tags.get("dimensionValue");
                    SubmitCore submitCount = new SubmitCore(userName);
                    for(List<Object> values:series.getValues()){
                        Long timestamp = sdf.parse(values.get(0).toString()).getTime();
                        float count = 0;
                        if(values.get(1)!=null) {
                            count = Float.parseFloat(values.get(1).toString());
                        }
                        submitCount.addPoint(timestamp,count);
                    }
                    submitCores.add(submitCount);
                }
            }
        }catch (Exception e){
            logger.error("查询cpu统计失败:{}",e.getMessage());
        }
        return submitCores;
    }








    public static void main(String[] args) {

        String time = "2016-11-05T01:00:00Z";
        try {
            System.out.println(sdf.parse(time).getTime());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        InfluxdbDao dao = new InfluxdbDao();
       for(SubmitCount submitCount:InfluxdbDao.querySubmitCount()){
           System.out.println(submitCount);
       }
//        for(TotalCore totalCore:InfluxdbDao.queryTotalCore()){
//            System.out.println(totalCore.getName());
//            System.out.println(totalCore.getCores());
//        }
//        for(String appId:dao.queryAppId()){
//            System.out.println(appId);
//        }

    }




}
