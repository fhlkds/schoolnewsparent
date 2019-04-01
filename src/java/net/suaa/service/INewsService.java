package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.Classify;
import net.suaa.domain.News;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface INewsService {
    public News getObjectById(Long id);

    public abstract boolean save(News news);

    public abstract News getObjById(Long id);

    public abstract boolean delete(Long id);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(News news);

    public abstract List<News> query(String query, Map param, int min, int max);
}
