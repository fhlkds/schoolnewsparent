package net.suaa.base;

import net.suaa.utils.CommUtil;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class GenericEntityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Object get(Class clazz, Serializable id){
        if (id == null)
            return null;
       return entityManager.find(clazz,id);
    }

    public void save(Object instance){
        entityManager.persist(instance);
    }

    public void remove(Class clazz,Serializable serializable){
        Object object = this.get(clazz,serializable);
        if(object != null)
            this.entityManager.remove(object);
    }
    public List<Object> find(Class clazz, final String queryStr, final Map params, final int begin, final int max){
        final Class claz = clazz;
                String clazzName = claz.getName();
                StringBuffer sb = new StringBuffer("select obj from ");
                sb.append(clazzName).append(" obj").append(" where ")
                        .append(queryStr);
                Query query = entityManager.createQuery(sb.toString());
                for (Iterator localIterator = params.keySet().iterator(); localIterator.hasNext();){
                    Object key = localIterator.next();
                    query.setParameter(key.toString(), params.get(key));
                }
                if ((begin >= 0) && (max > 0)){
                    query.setFirstResult(begin);
                    query.setMaxResults(max);
                }
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return query.getResultList();
    }

    public List query(final String queryStr, final Map params, final int begin, final int max){
                Query query = entityManager.createQuery(queryStr);
                if ((params != null) && (params.size() > 0)){
                    for (Iterator localIterator = params.keySet().iterator(); localIterator.hasNext();){
                        Object key = localIterator.next();
                        query.setParameter(key.toString(), params.get(key));
                    }
                }
                if ((begin >= 0) && (max > 0)){
                    query.setFirstResult(begin);
                    query.setMaxResults(max);
                }
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return query.getResultList();
    }
    public Object getBy(Class clazz, final String propertyName, final Object value){
        final Class claz = clazz;

                String clazzName = claz.getName();
                StringBuffer sb = new StringBuffer("select obj from ");
                sb.append(clazzName).append(" obj");
                Query query = null;
                if ((propertyName != null) && (value != null)){
                    sb.append(" where obj.").append(propertyName).append(" = :value");
                    query = entityManager.createQuery(sb.toString()).setParameter("value", value);
                }else{
                    query = entityManager.createQuery(sb.toString());
                }
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return query.getResultList();

    }
    public List executeNamedQuery(final String queryName, final Object[] params, final int begin, final int max){
                Query query = entityManager.createNamedQuery(queryName);
                int parameterIndex = 1;
                if ((params != null) && (params.length > 0)){
                    for (Object obj : params){
                        query.setParameter(parameterIndex++, obj);
                    }
                }
                if ((begin >= 0) && (max > 0)){
                    query.setFirstResult(begin);
                    query.setMaxResults(max);
                }
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return query.getResultList();

    }
    public List executeNativeNamedQuery(final String nnq){
          Query query = entityManager.createNativeQuery(nnq);
          return query.getResultList();
    }
    public List executeNativeQuery(final String nnq, final Map params, final int begin, final int max){
                Query query = entityManager.createNativeQuery(nnq);
                int parameterIndex = 1;
                if (params != null){
                    Iterator its = params.keySet().iterator();
                    while (its.hasNext()){
                        query.setParameter(CommUtil.null2String(its.next()),
                                params.get(its.next()));
                    }
                }
                if ((begin >= 0) && (max > 0)){
                    query.setFirstResult(begin);
                    query.setMaxResults(max);
                }
                return query.getResultList();
    }
    public List executeNativeQuery(final String nnq, final Object[] params, final int begin, final int max){
                Query query = entityManager.createNativeQuery(nnq);
                int parameterIndex = 1;
                if ((params != null) && (params.length > 0)){
                    for (Object obj : params){
                        query.setParameter(parameterIndex++, obj);
                    }
                }
                if ((begin >= 0) && (max > 0)){
                    query.setFirstResult(begin);
                    query.setMaxResults(max);
                }

                return query.getResultList();
    }

    public int executeNativeSQL(final String nnq){
                Query query = entityManager.createNativeQuery(nnq);
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return Integer.valueOf(query.executeUpdate());
    }
    public int batchUpdate(final String jpql, final Object[] params){
                Query query = entityManager.createQuery(jpql);
                int parameterIndex = 1;
                if ((params != null) && (params.length > 0)){
                    for (Object obj : params){
                        query.setParameter(parameterIndex++, obj);
                    }
                }
                query.setHint("org.hibernate.cacheable", Boolean.valueOf(true));
                return Integer.valueOf(query.executeUpdate());
    }
    public void update(Object instance){
        entityManager.merge(instance);
    }
    public void flush(){
          entityManager.getTransaction().commit();
    }

    }
