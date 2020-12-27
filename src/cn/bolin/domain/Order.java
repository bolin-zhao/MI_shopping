package cn.bolin.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Create By Bolin on 12.26
 */
public class Order {
    private String id;
    private Integer uid;
    private BigDecimal money;
    private String status;
    private Date time;
    private Integer aid;

    public Order(String id, Integer uid, BigDecimal money, String status, Date time, Integer aid) {
        this.id = id;
        this.uid = uid;
        this.money = money;
        this.status = status;
        this.time = time;
        this.aid = aid;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", uid=" + uid +
                ", money=" + money +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", aid=" + aid +
                '}';
    }
}
