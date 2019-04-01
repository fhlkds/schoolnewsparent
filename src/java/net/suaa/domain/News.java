package net.suaa.domain;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

/**
 * 新闻
 */
@Entity
@Table(name = "school_news")
public class News extends Identity{

    //标题
    private String title;

    //内容
    private String content;

    //栏目
    @ManyToOne(fetch = FetchType.LAZY)
    private Classify classify;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }
}
