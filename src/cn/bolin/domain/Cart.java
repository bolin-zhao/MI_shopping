package cn.bolin.domain;

import java.math.BigDecimal;

/**
 * Create By Bolin on 12.25
 */
public class Cart {
    private Integer id;
    private Integer pid;
    private Goods goods;
    private Integer num;
    private BigDecimal money;

    public Cart(Integer id, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }

    public Cart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", pid=" + pid +
                ", num=" + num +
                ", money=" + money +
                '}';
    }
}
