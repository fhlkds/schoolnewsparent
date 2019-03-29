package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.Student;
import net.suaa.domain.User;
import net.suaa.domain.UserConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IUserService {
    public User getObjectById(Long id);

    public abstract boolean save(User user);

    public abstract User getObjById(Long id);

    public abstract boolean delete(Long id);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(User user);

    public abstract List<User> query(String query, Map param, int min, int max);
    public UserConfig getUserConfig();
}
