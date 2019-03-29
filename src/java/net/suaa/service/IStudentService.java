package net.suaa.service;

import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.domain.Student;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract interface IStudentService {

    public Student getObjectById(Long id);

    public abstract boolean save(Student paramOrderForm);

    public abstract Student getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(Student paramOrderForm);

    public abstract List<Student> query(String paramString, Map paramMap, int paramInt1, int paramInt2);

}
