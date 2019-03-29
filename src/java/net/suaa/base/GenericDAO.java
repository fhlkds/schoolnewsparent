package net.suaa.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GenericDAO<T> implements IGenericDAO {

    protected Class<T> entityClass;

    @Autowired
    @Qualifier("genericEntityDAO")
    private GenericEntityDAO genericEntityDAO;

    public GenericDAO(){
        this.entityClass =
                ((Class) ((java.lang.reflect.ParameterizedType) getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void remove(Serializable paramSerializable){
          genericEntityDAO.remove(entityClass,paramSerializable);
    }

    @Override
    public void update(Object paramT) {
        genericEntityDAO.update(paramT);
    }

    @Override
    public Object getBy(String paramString, Object paramObject) {
        return genericEntityDAO.getBy(entityClass,paramString,paramObject);
    }

    @Override
    public List executeNamedQuery(String queryName, Object[] params, int begin, int max) {
        return genericEntityDAO.executeNamedQuery(queryName, params, begin, max);
    }

    @Override
    public List find(String query, Map params, int begin, int max) {
        return genericEntityDAO
                .find(this.entityClass, query, params, begin, max);
    }

    @Override
    public List query(String query, Map params, int begin, int max) {
        return genericEntityDAO.query(query, params, begin, max);
    }

    @Override
    public int batchUpdate(String jpql, Object[] params) {
        return genericEntityDAO.batchUpdate(jpql, params);
    }

    @Override
    public List executeNativeNamedQuery(String nnq) {
        return genericEntityDAO.executeNativeNamedQuery(nnq);
    }

    @Override
    public List executeNativeQuery(String nnq, Object[] params, int begin, int max) {
        return genericEntityDAO.executeNativeQuery(nnq, params, begin, max);
    }

    @Override
    public int executeNativeSQL(String nnq) {
        return genericEntityDAO.executeNativeSQL(nnq);
    }

    @Override
    public void flush() {
        genericEntityDAO.flush();
    }

    @Override
    public void save(Object paramT) {
        this.genericEntityDAO.save(paramT);
    }

    @Override
    public T get(Serializable serializable) {
        return (T)this.genericEntityDAO.get(this.entityClass,serializable);
    }

}
