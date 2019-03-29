package net.suaa.service.impl;

import net.suaa.core.query.GenericPageList;
import net.suaa.core.query.PageObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.dao.StudentDAO;
import net.suaa.domain.Student;
import net.suaa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentService implements IStudentService{

    @Autowired
    private StudentDAO studentDAO;

    @Override
    public boolean save(Student student) {
        try {
            this.studentDAO.save(student);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student getObjectById(Long id) {
        return this.studentDAO.get(id);
    }
    public Student getObjById(Long id){
        Student student = (Student)this.studentDAO.get(id);
        if (student != null){
            return student;
        }

        return null;
    }

    public boolean delete(Long id){
        try {
            this.studentDAO.remove(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean batchDelete(List<Serializable> studentIds){
        for (Serializable id : studentIds){
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
        GenericPageList pList = new GenericPageList(Student.class, query,
                params, this.studentDAO);
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

    public boolean update(Student student){
        try {
            this.studentDAO.update(student);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<Student> query(String query, Map params, int begin, int max){
        return this.studentDAO.query(query, params, begin, max);
    }
}
