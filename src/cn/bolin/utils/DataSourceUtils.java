package cn.bolin.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author: bolin
 *
 * 工具类 DruidUtils.java 连接池 阿里的连接池 druid
 */
public class DataSourceUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> threadLocal;

    static {
        try {
            threadLocal=new ThreadLocal<>();
            Properties properties=new Properties();
            InputStream is=DataSourceUtils.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(is);
            is.close();
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池失败");
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    public static Connection getConnection() throws Exception{
        Connection conn = threadLocal.get();
        if(conn==null){
            conn  = dataSource.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void startTransaction() throws Exception{
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    public static void commit() throws Exception{
        Connection conn = getConnection();
        conn.commit();
    }

    public static void rollback() throws Exception{
        Connection conn = getConnection();
        conn.rollback();
    }

    public static void close() throws Exception{
        Connection conn = getConnection();
        conn.close();
        threadLocal.remove();
    }

}
