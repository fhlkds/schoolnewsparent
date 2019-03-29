package net.suaa.service.impl;

import net.suaa.core.query.GenericPageList;
import net.suaa.core.query.PageObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.dao.ClassifyDAO;
import net.suaa.domain.Classify;
import net.suaa.service.IClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClassifyServiceImpl implements IClassifyService {

    @Autowired
    private ClassifyDAO classifyDAO;

    @Override
    public boolean save(Classify classify) {
        try {
            this.classifyDAO.save(classify);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Classify getObjectById(Long id) {
        return this.classifyDAO.get(id);
    }
    public Classify getObjById(Long id){
        Classify user = (Classify)this.classifyDAO.get(id);
        if (user != null){
            return user;
        }

        return null;
    }

    public boolean delete(Long id){
        try {
            this.classifyDAO.remove(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean batchDelete(List<Serializable> classifyIds){
        for (Serializable id : classifyIds){
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
        GenericPageList pList = new GenericPageList(Classify.class, query,
                params, this.classifyDAO);
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

    public boolean update(Classify user){
        try {
            this.classifyDAO.update(user);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<Classify> query(String query, Map params, int begin, int max){
        return this.classifyDAO.query(query, params, begin, max);
    }

}
