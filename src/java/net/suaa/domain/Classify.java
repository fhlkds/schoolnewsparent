package net.suaa.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻栏目
 */
@Entity
@Table(name = "school_classify")
public class Classify extends Identity {

    @ManyToMany(mappedBy = "cus")
    private List<User> users = new ArrayList<>();

    @Autowired
    private String classifyName;
    //子分类
    @OneToMany(mappedBy = "parent")
    @OrderBy("sequence asc")
    private List<Classify> childs = new ArrayList<>();

    //父分类
    @ManyToOne(fetch = FetchType.LAZY)
    private Classify parent;

    @OneToMany(mappedBy = "classify")
    private List<News> news = new ArrayList<>();

    @Column(columnDefinition = "int default 1")
    private int serial;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Classify getParent() {
        return parent;
    }

    public void setParent(Classify parent) {
        this.parent = parent;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Classify> getChilds() {
        return childs;
    }

    public List<News> getNews() {
        return news;
    }
}
