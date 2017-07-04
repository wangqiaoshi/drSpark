package cn.qs.consumer.bean;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by wqs on 16/10/18.
 */
public class Factory {


    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() throws Exception{
        if(sqlSessionFactory == null) {
            String resource = "config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;
    }


    public static void main(String[] args)throws Exception {
        Factory.getSqlSessionFactory();
//        SqlSession session = sqlSessionFactory.openSession();
//        List<StatisticsMetric> statisticsMetrics  = session.selectList("bean.getMetrics");
//        System.out.println(statisticsMetrics.size());
//        for(StatisticsMetric metric:statisticsMetrics){
//            System.out.println(metric);
//        }

    }
}
