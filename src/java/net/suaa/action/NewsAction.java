package net.suaa.action;

import net.suaa.core.mv.JModelAndView;
import net.suaa.core.security.support.SecurityUserHolder;
import net.suaa.domain.Classify;
import net.suaa.domain.News;
import net.suaa.domain.User;
import net.suaa.service.*;
import net.suaa.utils.CommUtil;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class NewsAction {
    @Autowired
    private IClassifyService classifyService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private INewsService newsService;

    @RequestMapping("/admin/newsManagement.htm")
    public ModelAndView news(HttpServletResponse response, HttpServletRequest request){
        ModelAndView mv = new JModelAndView("/page/NewsManagement.html",this.sysConfigService.getSysConfig(),this.userConfigService.getUserConfig(),request,response);
        User currentUser = SecurityUserHolder.getCurrentUser();
        if(currentUser == null){
            try {
                mv = null;
                response.sendRedirect("/login.htm");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            mv.addObject("classifys",currentUser.getCus());
        }
        return mv;
    }
    @RequestMapping("/save_news.htm")
    public void saveNews(HttpServletRequest request,HttpServletResponse response,String classify_id,String title,String content){
        User user = SecurityUserHolder.getCurrentUser();
        Classify classify = this.classifyService.getObjById(CommUtil.null2Long(classify_id));
        Map map = new HashMap();
        if(user != null && classify != null && title!=null && content != null){
            News news = new News();
            news.setAddTime(new Date());
            news.setClassify(classify);
            news.setContent(CommUtil.null2String(content));
            news.setTitle(CommUtil.null2String(title));
            this.newsService.save(news);
            map.put("status",1);
        }
        returnView(response,map);
    }
    public void returnView(HttpServletResponse response,Map res){
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(Json.toJson(res, JsonFormat.compact()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
