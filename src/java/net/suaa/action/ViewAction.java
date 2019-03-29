package net.suaa.action;

import net.suaa.core.domain.virtual.SysMap;
import net.suaa.core.mv.JModelAndView;
import net.suaa.core.query.UserObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.query.support.IQuery;
import net.suaa.core.query.support.IQueryObject;
import net.suaa.core.security.SecurityManager;
import net.suaa.core.security.support.SecurityUserHolder;
import net.suaa.domain.User;
import net.suaa.service.ISysConfigService;
import net.suaa.service.IUserConfigService;
import net.suaa.service.IUserService;
import net.suaa.utils.CommUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ViewAction {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IUserConfigService userConfigService;




    @RequestMapping("/index.htm")
    public ModelAndView index(HttpServletResponse response,HttpServletRequest request){
        ModelAndView mv = new JModelAndView("/index.html", this.sysConfigService.getSysConfig(), this.userConfigService.getUserConfig(), request, response);
        if(SecurityUserHolder.getCurrentUser() == null) {
            try {
                response.sendRedirect("/login.htm");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mv;
    }

    @RequestMapping("/admin/accountManagement.htm")
    public ModelAndView accountManager(HttpServletResponse response, HttpServletRequest request,String currentPage){
        ModelAndView mv = new JModelAndView("/page/AccountManagement.html",this.sysConfigService.getSysConfig(),this.userConfigService.getUserConfig(),request,response);
        if(SecurityUserHolder.getCurrentUser() == null){
            try {
                response.sendRedirect("/login.htm");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            UserObject uo = new UserObject();
            if(currentPage == null)
                currentPage = 1+"";
            uo.setCurrentPage(CommUtil.null2Int(currentPage));
            uo.addQuery("obj.deleteStatus",new SysMap("deleteStatus",false),"=");
            IPageList ipl = this.userService.list(uo);
            String url = CommUtil.getURL(request)+"/admin/accountManagement.htm";
            CommUtil.saveIPageList2ModelAndView("",url , "", ipl, mv);
        }
        return mv;
    }



}
