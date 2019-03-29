package net.suaa.core.query;

import org.springframework.web.servlet.ModelAndView;

public class ClassifyObject extends QueryObject {
    public ClassifyObject(){}
    public ClassifyObject(String currentPage, ModelAndView mv, String orderBy, String orderType){
        super(currentPage,mv,orderBy,orderType);
    }
}
