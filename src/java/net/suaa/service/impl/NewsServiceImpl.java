package net.suaa.service.impl;

import net.suaa.core.query.GenericPageList;
import net.suaa.core.query.PageObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.dao.NewsDAO;
import net.suaa.domain.Classify;
import net.suaa.domain.News;
import net.suaa.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsDAO newsDAO;
    @Override
    public boolean save(News news) {
        try {
            this.newsDAO.save(news);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public News getObjectById(Long id) {
        return this.newsDAO.get(id);
    }

    public News getObjById(Long id){
        News news = (News)this.newsDAO.get(id);
        if (news != null){
            return news;
        }

        return null;
    }

    public boolean delete(Long id){
        try {
            this.newsDAO.remove(id);
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
        GenericPageList pList = new GenericPageList(News.class, query,
                params, this.newsDAO);
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

    public boolean update(News news){
        try {
            this.newsDAO.update(news);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<News> query(String query, Map params, int begin, int max){
        return this.newsDAO.query(query, params, begin, max);
    }

}
