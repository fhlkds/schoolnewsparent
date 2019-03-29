package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.SysConfig;
import net.suaa.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ISysConfigService {

    public SysConfig getObjectById(Long id);

    public abstract boolean save(SysConfig sysConfig);

    public abstract SysConfig getObjById(Long id);

    public abstract boolean delete(Long id);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(SysConfig sysConfig);

    public abstract List<SysConfig> query(String query, Map param, int min, int max);
    public abstract SysConfig getSysConfig();
}
