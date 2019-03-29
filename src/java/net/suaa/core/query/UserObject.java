package net.suaa.core.query;

import net.suaa.core.query.support.IQueryObject;
import org.springframework.web.servlet.ModelAndView;

public class UserObject extends QueryObject {

    public UserObject(){}
    public UserObject(String currentPage, ModelAndView mv, String orderBy, String orderType){
        super(currentPage,mv,orderBy,orderType);
    }
}
