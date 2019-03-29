package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.Classify;
import net.suaa.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IClassifyService {

    public Classify getObjectById(Long id);

    public abstract boolean save(Classify classify);

    public abstract Classify getObjById(Long id);

    public abstract boolean delete(Long id);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(Classify classify);

    public abstract List<Classify> query(String query, Map param, int min, int max);
}
