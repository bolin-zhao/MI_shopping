package cn.bolin.domain;

/**
 * Create By Bolin on 12.23
 */
public class GoodsType {
    private Integer id;
    private String name;
    private Integer level; //级别
    private Integer parent; // 上一级别

    public GoodsType(){

    }

    public GoodsType(Integer id, String name, Integer level, Integer parent) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parent=" + parent +
                '}';
    }
}
