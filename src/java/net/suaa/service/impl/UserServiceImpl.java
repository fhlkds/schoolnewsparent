package net.suaa.service.impl;

import net.suaa.core.query.GenericPageList;
import net.suaa.core.query.PageObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.core.security.support.SecurityUserHolder;
import net.suaa.dao.UserDAO;
import net.suaa.domain.Student;
import net.suaa.domain.User;
import net.suaa.domain.UserConfig;
import net.suaa.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean save(User user) {
        try {
            this.userDAO.save(user);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getObjectById(Long id) {
        return this.userDAO.get(id);
    }
    public User getObjById(Long id){
        User user = (User)this.userDAO.get(id);
        if (user != null){
            return user;
        }

        return null;
    }

    public boolean delete(Long id){
        try {
            this.userDAO.remove(id);
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
        GenericPageList pList = new GenericPageList(User.class, query,
                params, this.userDAO);
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

    public boolean update(User user){
        try {
            this.userDAO.update(user);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<User> query(String query, Map params, int begin, int max){
        return this.userDAO.query(query, params, begin, max);
    }

    public UserConfig getUserConfig(){
        User u = SecurityUserHolder.getCurrentUser();
        UserConfig config = null;
        if (u != null){
            User user = (User)this.userDAO.get(u.getId());
            if (user != null)
                config = user.getConfig();
        }else{
            config = new UserConfig();
        }

        return config;
    }
}
