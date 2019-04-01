package net.suaa.action;

import net.suaa.core.domain.virtual.SysMap;
import net.suaa.core.mv.JModelAndView;
import net.suaa.core.query.ClassifyObject;
import net.suaa.core.query.UserObject;
import net.suaa.core.query.support.IPageList;
import net.suaa.core.security.support.SecurityUserHolder;
import net.suaa.domain.Classify;
import net.suaa.domain.User;
import net.suaa.service.IClassifyService;
import net.suaa.service.ISysConfigService;
import net.suaa.service.IUserConfigService;
import net.suaa.service.IUserService;
import net.suaa.utils.CommUtil;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClassifyAction {

    @Autowired
    private IClassifyService classifyService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IUserConfigService userConfigService;

    @RequestMapping("/admin/columnManagement.htm")
    public ModelAndView columnManagement(HttpServletResponse response, HttpServletRequest request,String currentPage){
        ModelAndView mv = new JModelAndView("/page/ColumnManagement.html",this.sysConfigService.getSysConfig(),this.userConfigService.getUserConfig(),request,response);
        if(SecurityUserHolder.getCurrentUser() == null){
            try {
                response.sendRedirect("/login.htm");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ClassifyObject co = new ClassifyObject();
            if(currentPage == null)
                co.setCurrentPage(1);
            co.setCurrentPage(CommUtil.null2Int(currentPage));
            co.addQuery("obj.deleteStatus",new SysMap("deleteStatus",false),"=");
            co.setPageSize(8);
            IPageList ipl = this.classifyService.list(co);
            String url = CommUtil.getURL(request)+"/admin/columnManagement.htm";
            CommUtil.saveIPageList2ModelAndView("", url, "", ipl, mv);
        }
        return mv;
    }

    @RequestMapping("/admin/addClassify.htm")
    public void addClassify(HttpServletRequest request,HttpServletResponse response,String classIfy_name,String classify_serial){
        User user = SecurityUserHolder.getCurrentUser();
        Map res = new HashMap();
        if(user != null && !CommUtil.null2String(classIfy_name).equals("")){
            Map param = new HashMap();
            param.put("classifyName",CommUtil.null2String(classIfy_name));
            List<Classify> list = this.classifyService.query("select obj from Classify obj where obj.classifyName=:classifyName and obj.deleteStatus=false",param,-1,-1);
            if(list == null || list.size() != 0){
                param.put("status",2);//分类名重复
            }else{
                Classify classify = new Classify();
                classify.setClassifyName(CommUtil.null2String(classIfy_name));
                if(CommUtil.null2Int(classify_serial) != 0){
                    classify.setSerial(CommUtil.null2Int(classify_serial));
                }
                res.put("classify",classify);
                this.classifyService.save(classify);
                res.put("status",1);
            }
        }else{
            res.put("status",3);//分类名不能为null
        }
        returnView(response,res);
    }
    @RequestMapping("/admin/edit_classify.htm")
    public void editClassify(HttpServletResponse response,HttpServletRequest request,String classify_id,String classify_name,String classify_serial){
        User user = SecurityUserHolder.getCurrentUser();
        Map res = new HashMap();
        Classify classify = this.classifyService.getObjById(CommUtil.null2Long(classify_id));
        if(classify == null || user == null){
            res.put("status",2);
        }else{
            if(CommUtil.null2String(classify_name).equals("") || CommUtil.null2Int(classify_id) == 0){
                res.put("status",3);
            }else{
                Map map = new HashMap();
                map.put("classifyName",CommUtil.null2String(classify_name));
                map.put("classifyId",CommUtil.null2Long(classify_id));
                List<Classify> list = this.classifyService.query("select obj from Classify obj where obj.classifyName=:classifyName and obj.id <>:classifyId",map,-1,-1);
                if(list==null || list.size() == 0){
                    classify.setSerial(CommUtil.null2Int(classify_id));
                    classify.setClassifyName(CommUtil.null2String(classify_name));
                    this.classifyService.update(classify);
                    Classify c = new Classify();
                    c.setClassifyName(classify.getClassifyName());
                    c.setSerial(classify.getSerial());
                    res.put("status",1);
                    res.put("classify",c);
                }else{
                    res.put("status",5);
                }

            }
        }
        returnView(response,res);
    }

    @RequestMapping("/admin/delete_classify.htm")
    public void deleteClassify(HttpServletRequest request,HttpServletResponse response,String classify_id){
        User user = SecurityUserHolder.getCurrentUser();
        Classify classify = this.classifyService.getObjById(CommUtil.null2Long(classify_id));
        Map res = new HashMap();
        if(user != null && classify != null){
            res.put("status",1);
            classify.setDeleteStatus(true);
            this.classifyService.update(classify);
        }else{
            res.put("status",2);
        }
        returnView(response,res);
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
