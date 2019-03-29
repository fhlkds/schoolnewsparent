package net.suaa.service.impl;

import net.suaa.core.query.GenericPageList;
import net.suaa.core.query.PageObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.dao.SysConfigDAO;
import net.suaa.dao.UserDAO;
import net.suaa.domain.SysConfig;
import net.suaa.domain.User;
import net.suaa.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigDAO sysConfigDAO;

    @Override
    public boolean save(SysConfig user) {
        try {
            this.sysConfigDAO.save(user);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SysConfig getObjectById(Long id) {
        return this.sysConfigDAO.get(id);
    }
    public SysConfig getObjById(Long id){
        SysConfig sysConfig = (SysConfig)this.sysConfigDAO.get(id);
        if (sysConfig!= null){
            return sysConfig;
        }

        return null;
    }

    public boolean delete(Long id){
        try {
            this.sysConfigDAO.remove(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean batchDelete(List<Serializable> userIds){
        for (Serializable id : userIds){
            delete((Long)id);
        }

        return true;
    }

    public IPageList list(IQueryObject properties){
        if (properties == null){
            return null;
        }
        String query = properties.getQuery();
        Map params = properties.getParameters();
        GenericPageList pList = new GenericPageList(SysConfig.class, query,
                params, this.sysConfigDAO);
        if (properties != null){
            PageObject pageObj = properties.getPageObj();
            if (pageObj != null)
                pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
                        .getCurrentPage().intValue(), pageObj.getPageSize() == null ? 0 :
                        pageObj.getPageSize().intValue());
        }else{
            pList.doList(0, -1);
        }

        return pList;
    }

    public boolean update(SysConfig sysConfig){
        try {
            this.sysConfigDAO.update(sysConfig);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<SysConfig> query(String query, Map params, int begin, int max){
        return this.sysConfigDAO.query(query, params, begin, max);
    }
    public SysConfig getSysConfig(){
        List configs = this.sysConfigDAO.query(
                "select obj from SysConfig obj", null, -1, -1);
        if ((configs != null) && (configs.size() > 0)){
            SysConfig sc = (SysConfig)configs.get(0);
            if (sc.getUploadFilePath() == null){
                sc.setUploadFilePath("upload");
            }
            if (sc.getSysLanguage() == null){
                sc.setSysLanguage("zh_cn");
            }
            if ((sc.getWebsiteName() == null) || (sc.getWebsiteName().equals(""))){
                sc.setWebsiteName("wemall");
            }
            if ((sc.getCloseReason() == null) || (sc.getCloseReason().equals(""))){
                sc.setCloseReason("系统维护中...");
            }
            if ((sc.getTitle() == null) || (sc.getTitle().equals(""))){
                sc.setTitle("wemall多用户商城系统V2.0版");
            }
            if ((sc.getImageSaveType() == null) ||
                    (sc.getImageSaveType().equals(""))){
                sc.setImageSaveType("sidImg");
            }
            if (sc.getImageFilesize() == 0){
                sc.setImageFilesize(1024);
            }
            if (sc.getSmallWidth() == 0){
                sc.setSmallWidth(160);
            }
            if (sc.getSmallHeight() == 0){
                sc.setSmallHeight(160);
            }
            if (sc.getMiddleWidth() == 0){
                sc.setMiddleWidth(300);
            }
            if (sc.getMiddleHeight() == 0){
                sc.setMiddleHeight(300);
            }
            if (sc.getBigHeight() == 0){
                sc.setBigHeight(1024);
            }
            if (sc.getBigWidth() == 0){
                sc.setBigWidth(1024);
            }
           /* if ((sc.getImageSuffix() == null) || (sc.getImageSuffix().equals(""))){
                sc.setImageSuffix("gif|jpg|jpeg|bmp|png|tbi");
            }
            if (sc.getStoreImage() == null){
                Accessory storeImage = new Accessory();
                storeImage.setPath("resources/style/common/images");
                storeImage.setName("store.jpg");
                sc.setStoreImage(storeImage);
            }
            if (sc.getGoodsImage() == null){
                Accessory goodsImage = new Accessory();
                goodsImage.setPath("resources/style/common/images");
                goodsImage.setName("good.jpg");
                sc.setGoodsImage(goodsImage);
            }
            if (sc.getMemberIcon() == null){
                Accessory memberIcon = new Accessory();
                memberIcon.setPath("resources/style/common/images");
                memberIcon.setName("member.jpg");
                sc.setMemberIcon(memberIcon);
            }*/
            if ((sc.getSecurityCodeType() == null) ||
                    (sc.getSecurityCodeType().equals(""))){
                sc.setSecurityCodeType("normal");
            }
            if ((sc.getWebsiteCss() == null) || (sc.getWebsiteCss().equals(""))){
                sc.setWebsiteCss("default");
            }
            return sc;
        }
        SysConfig sc = new SysConfig();
        sc.setUploadFilePath("upload");
        sc.setWebsiteName("wemall");
        sc.setSysLanguage("zh_cn");
        sc.setTitle("wemall多用户商城系统V2.0版");
        sc.setSecurityCodeType("normal");
       // sc.setEmailEnable(true);
        sc.setCloseReason("系统维护中...");
        sc.setImageSaveType("sidImg");
        sc.setImageFilesize(1024);
        sc.setSmallWidth(160);
        sc.setSmallHeight(160);
        sc.setMiddleHeight(300);
        sc.setMiddleWidth(300);
        sc.setBigHeight(1024);
        sc.setBigWidth(1024);
       // sc.setImageSuffix("gif|jpg|jpeg|bmp|png|tbi");
        //sc.setComplaint_time(30);
        sc.setWebsiteCss("default");
        //Accessory goodsImage = new Accessory();
        //goodsImage.setPath("resources/style/common/images");
        //goodsImage.setName("good.jpg");
        /*sc.setGoodsImage(goodsImage);
        Accessory storeImage = new Accessory();
        storeImage.setPath("resources/style/common/images");
        storeImage.setName("store.jpg");
        sc.setStoreImage(storeImage);
        Accessory memberIcon = new Accessory();
        memberIcon.setPath("resources/style/common/images");
        memberIcon.setName("member.jpg");
        sc.setMemberIcon(memberIcon);*/

        return sc;
    }
}
