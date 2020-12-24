package cn.bolin.dao.impl;

import cn.bolin.dao.GoodsDao;
import cn.bolin.domain.Goods;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Create By Bolin on
 */
public class GoodsDaoImpl implements GoodsDao {
    @Override
    public long getCount(String condition) {  //" typeId=1";
        String  sql="select count(*) from tb_goods";
        if(condition!=null&&condition.trim().length()!=0){
            sql=sql+" where "+condition;   // select count(*) from tb_goods  where  typeId=1
        }
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return (long) qr.query(sql, new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询商品个数失败", e);
        }

    }

    @Override
    public List<Goods> findPageByWhere(int pageNum, int pageSize, String condition) {
        String  sql="select * from tb_goods";
        if(condition!=null&&condition.trim().length()!=0){
            sql=sql+" where "+condition;   // select *  from tb_goods  where  typeId=1
        }
        sql+=" order by id limit ?,?";
        // select *  from  tb_goods  where  typeId=1  order by id limit ?,?
        // select * from tb_goods order by id limit ?,?

        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query(sql, new BeanListHandler<Goods>(Goods.class),(pageNum-1)*pageSize,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException("分页查询失败", e);
        }
    }

    @Override
    public Goods findByGid(int gid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return  qr.query("select * from tb_goods where id = ?", new BeanHandler<Goods>(Goods.class), gid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查询商品失败", e);
        }
    }


}
