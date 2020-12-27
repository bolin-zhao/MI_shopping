package cn.bolin.domain;

import java.math.BigDecimal;

/**
 * Create By Bolin on 12.26
 */
public class OrderDetail {
    private Integer id;
    private String oid;
    private Integer pid;
    private Integer num;
    private BigDecimal money;

    public OrderDetail(Integer id, String oid, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.oid = oid;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }

    public OrderDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", num=" + num +
                ", money=" + money +
                '}';
    }
}
