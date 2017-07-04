package cn.qs.consumer.dao;

import cn.qs.consumer.bean.Factory;
import cn.qs.consumer.bean.StatisticsMetric;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wqs on 16/10/18.
 */
public class StatisticsMetricDao {

    private Logger logger = LoggerFactory.getLogger(StatisticsMetricDao.class);


    public List<StatisticsMetric> getAllStatisticsMetrics(){
        List<StatisticsMetric> statisticsMetricses ;
        try {
            SqlSessionFactory sqlSessionFactory = Factory.getSqlSessionFactory();
            SqlSession sqlSession = sqlSessionFactory.openSession();
            statisticsMetricses =  sqlSession.selectList("bean.getMetrics");
            logger.info("load metrics from mysql");
        }catch (Exception e){
            logger.error("获取指标失败:{}",e.getMessage());
            statisticsMetricses = new ArrayList<StatisticsMetric>();
        }
        return statisticsMetricses;
    }


    public static void main(String[] args) {

        StatisticsMetricDao dao = new StatisticsMetricDao();
        for(StatisticsMetric metric:dao.getAllStatisticsMetrics()){
            System.out.println(metric.getDimension());
        }

    }

}
