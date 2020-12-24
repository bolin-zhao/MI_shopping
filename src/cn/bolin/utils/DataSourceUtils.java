package cn.bolin.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: bolin
 *
 * 工具类 DruidUtils.java 连接池 阿里的连接池 druid
 */
public class DataSourceUtils {
    private static DruidDataSource dataSource;
    static {
        try {
        //加载配置文件
        Properties properties = new Properties();
        //加载配置文件使用类加载器加载配置文件
        InputStream is = DataSourceUtils.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(is);
        is.close();
            dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池失败");
        }
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
}
