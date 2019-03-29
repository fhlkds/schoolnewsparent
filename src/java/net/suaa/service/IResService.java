package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.Res;

import java.util.List;
import java.util.Map;

public interface IResService {
    public abstract boolean save(Res paramRes);

    public abstract boolean delete(Long paramLong);

    public abstract boolean update(Res paramRes);

    public abstract Res getObjById(Long paramLong);

    public abstract List<Res> query(String paramString, Map paramMap, int paramInt1, int paramInt2);

    public abstract IPageList list(IQueryObject paramIQueryObject);
}
