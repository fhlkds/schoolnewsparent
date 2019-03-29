package net.suaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "school_sysconfig")
public class SysConfig extends Identity{
    //标题
    private String title;
    //关键字
    private String keywords;
    //描述
    private String description;

    @Column(columnDefinition = "bit default 0")
    private boolean second_domain_open;

    //图片web服务
    private String imageWebServer;

    //系统语言
    private String sysLanguage;

    //下载路径
    private String uploadFilePath;

    //网站名称
    private String websiteName;

    //关闭原因
    @Lob
    @Column(columnDefinition = "LongText")
    private String closeReason;

    //图片保存类型
    private String imageSaveType;

    //图片文件大小
    private int imageFilesize;
    //小宽度
    private int smallWidth;
    //小高度
    private int smallHeight;
    //中等宽度
    private int middleWidth;
    //中等高度
    private int middleHeight;
    //大宽度
    private int bigWidth;
    //大高度
    private int bigHeight;
    private boolean integral;
    private boolean integralStore;
    //安全码类型
    private String securityCodeType;
    @Column(columnDefinition = "varchar(255) default 'blue' ")
    private String websiteCss;

    public String getSecurityCodeType() {
        return securityCodeType;
    }

    public void setSecurityCodeType(String securityCodeType) {
        this.securityCodeType = securityCodeType;
    }

    public String getWebsiteCss() {
        return websiteCss;
    }

    public void setWebsiteCss(String websiteCss) {
        this.websiteCss = websiteCss;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getImageSaveType() {
        return imageSaveType;
    }

    public void setImageSaveType(String imageSaveType) {
        this.imageSaveType = imageSaveType;
    }

    public int getImageFilesize() {
        return imageFilesize;
    }

    public void setImageFilesize(int imageFilesize) {
        this.imageFilesize = imageFilesize;
    }

    public int getSmallWidth() {
        return smallWidth;
    }

    public void setSmallWidth(int smallWidth) {
        this.smallWidth = smallWidth;
    }

    public int getSmallHeight() {
        return smallHeight;
    }

    public void setSmallHeight(int smallHeight) {
        this.smallHeight = smallHeight;
    }

    public int getMiddleWidth() {
        return middleWidth;
    }

    public void setMiddleWidth(int middleWidth) {
        this.middleWidth = middleWidth;
    }

    public int getMiddleHeight() {
        return middleHeight;
    }

    public void setMiddleHeight(int middleHeight) {
        this.middleHeight = middleHeight;
    }

    public int getBigWidth() {
        return bigWidth;
    }

    public void setBigWidth(int bigWidth) {
        this.bigWidth = bigWidth;
    }

    public int getBigHeight() {
        return bigHeight;
    }

    public void setBigHeight(int bigHeight) {
        this.bigHeight = bigHeight;
    }

    public boolean isIntegral() {
        return integral;
    }

    public void setIntegral(boolean integral) {
        this.integral = integral;
    }

    public boolean isIntegralStore() {
        return integralStore;
    }

    public void setIntegralStore(boolean integralStore) {
        this.integralStore = integralStore;
    }

    public String getSysLanguage() {
        return sysLanguage;
    }

    public void setSysLanguage(String sysLanguage) {
        this.sysLanguage = sysLanguage;
    }

    public String getImageWebServer() {
        return imageWebServer;
    }

    public void setImageWebServer(String imageWebServer) {
        this.imageWebServer = imageWebServer;
    }

    public boolean isSecond_domain_open() {
        return second_domain_open;
    }

    public void setSecond_domain_open(boolean second_domain_open) {
        this.second_domain_open = second_domain_open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
