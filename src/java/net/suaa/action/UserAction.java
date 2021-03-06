package net.suaa.action;

import com.alibaba.fastjson.JSONObject;
import net.suaa.core.annotation.SecurityMapping;
import net.suaa.core.mv.JModelAndView;
import net.suaa.core.security.support.SecurityUserHolder;
import net.suaa.domain.Classify;
import net.suaa.domain.SysConfig;
import net.suaa.domain.User;
import net.suaa.service.IClassifyService;
import net.suaa.service.ISysConfigService;
import net.suaa.service.IUserConfigService;
import net.suaa.service.IUserService;
import net.suaa.utils.CommUtil;
import net.suaa.utils.Md5Encrypt;
import net.suaa.utils.ResponseUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

@Controller
public class UserAction {

    private static final Logger logger = LoggerFactory.getLogger(UserAction.class);


    @Autowired
    private IUserService userService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private IClassifyService classifyService;


    @RequestMapping("/login.htm")
    public ModelAndView login(HttpServletResponse response,HttpServletRequest request){
        ModelAndView mv = new JModelAndView("/login.html",this.sysConfigService.getSysConfig(),this.userConfigService.getUserConfig(),request,response);
        HttpSession session = request.getSession();
        mv.addObject("userName",session.getAttribute("userName"));
        mv.addObject("password",session.getAttribute("password"));
        mv.addObject("error",session.getAttribute("error"));
        session.removeAttribute("userName");
        session.removeAttribute("password");
        session.removeAttribute("error");
        logger.info("到达登录页------>用户名:{}密码:{}错误信息:{}",session.getAttribute("userName"),session.getAttribute("password"),session.getAttribute("error"));
        return mv;

    }
    @RequestMapping("/admin/add_user.htm")
    public void addUser(HttpServletResponse response, HttpServletRequest request,String userName,String password){
        int ret = 1;//1：成功；2：用户没有登录；3：账号或者密码为null
        Map map = new HashMap();
        User user = SecurityUserHolder.getCurrentUser();
        if(user!=null){
            if(!CommUtil.null2String(userName).equals("") && !CommUtil.null2String(password).equals("")){
                Map param = new HashMap();
                param.put("userName",CommUtil.null2String(userName));
                List<User> checkUsers = this.userService.query("select obj from User obj where obj.userName ={}userName  and obj.deleteStatus =false",param,-1,-1);
                if(checkUsers == null || checkUsers.size()==0){
                    User addUser = new User();
                    addUser.setUserName(CommUtil.null2String(userName));
                    addUser.setPassword(CommUtil.null2String(Md5Encrypt.md5(password).toUpperCase()));
                    userService.save(addUser);
                    User resUser = new User();
                    resUser.setId(addUser.getId());
                    resUser.setUserName(addUser.getUserName());
                    map.put("user",resUser);
                }else{
                    ret = 4;
                }

            }else {
                ret = 3;
            }
        }else{
          ret = 2;
        }
        logger.info("添加用户，状态:{}添加的用户名:{}",ret,userName);
        map.put("status",ret);
        returnView(response,map);
    }

    @RequestMapping("/loging.htm")
    public ModelAndView loging(HttpServletResponse response,HttpServletRequest request,String userName,String password,String valida){
        ModelAndView mv = null;
        Map res = new HashMap();
        Map param = new HashMap();
        HttpSession session = request.getSession();
        String verifyCode = CommUtil.null2String(session.getAttribute("verify_code"));
        request.getSession().removeAttribute("verify_code");
        String userNameStr = CommUtil.null2String(userName);
        String passwordStr = Md5Encrypt.md5(CommUtil.null2String(password)).toLowerCase();
        if (verifyCode != null && verifyCode.equals(CommUtil.null2String(valida))) {
            res.put("status", 0);
            param.put("userName",userNameStr);
            param.put("password",passwordStr);
            List<User> users = this.userService.query("select obj from User obj where obj.userName=:userName and obj.password=:password", param, -1, -1);
            if (users != null && users.size() > 0) {
                session.setAttribute("user",users.get(0));
                Cookie wemall_user_session = new Cookie("school_user_session", users.get(0).getId().toString());
                wemall_user_session.setDomain(CommUtil.generic_domain(request));
                response.addCookie(wemall_user_session);
                logger.info("用户{}登录成功",userName);
                try {
                    response.sendRedirect("/index.htm");
                }catch (Exception e){
                    e.printStackTrace();
                }
               // mv = new JModelAndView("/index.html",this.sysConfigService.getSysConfig(),this.userConfigService.getUserConfig(),request,response);
            }else{
                session.setAttribute("userName",userName);
                session.setAttribute("error","账号或者密码有误！");
                logger.info("用户{}登录失败,账号或者密码有误！",userName);
                try {
                    response.sendRedirect("/login.htm");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            logger.info("用户:{}登录失败，验证码有误，错误验证码:{}正确验证码:{}",userName,valida,verifyCode);
            session.setAttribute("userName",userName);
            session.setAttribute("password",password);
            session.setAttribute("error","验证码有误！");
            try {
                response.sendRedirect("/login.htm");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return mv;
    }
    @RequestMapping("/admin/edit_user.htm")
    public void editUser(HttpServletRequest request,HttpServletResponse response,String user_id,String user_name,String user_password){
        User user = SecurityUserHolder.getCurrentUser();
        Map res = new HashMap();
        if (user != null && CommUtil.null2String(user_name) != null){
            User editUser = userService.getObjById(CommUtil.null2Long(user_id));
            if(editUser != null){
                Map param = new HashMap();
                param.put("userName",CommUtil.null2String(user_name));
                param.put("userId",editUser.getId());
                List<User> checkedUsers = userService.query("select obj from User obj where obj.userName=:userName and obj.id <>:userId and obj.deleteStatus =false",param,-1,-1);
                if(checkedUsers.size() > 0){
                    res.put("status",5);//用户用户名重复
                }else{
                    editUser.setUserName(CommUtil.null2String(user_name));
                    if(CommUtil.null2String(user_password) != null){
                        editUser.setPassword(Md5Encrypt.md5(CommUtil.null2String(user_password)).toUpperCase());
                    }
                    userService.update(editUser);
                    res.put("status",1);//修改成功
                }
            }else{
                res.put("status",4);//要修改的用户不存在
            }
        }else{
            res.put("status",3);//没有登录
        }
        logger.info("添加用户:{}状态:{}",user_name,res.get("status"));
        returnView(response,res);
    }

    @RequestMapping("/admin/delete_user.htm")
    public void DeleteUser(HttpServletResponse response,HttpServletRequest request,String userId){
        User user = SecurityUserHolder.getCurrentUser();
        User deleteUser = userService.getObjById(CommUtil.null2Long(userId));
        Map res = new HashMap();
        res.put("status",2);
        if(user != null && deleteUser != null){
            deleteUser.setDeleteStatus(true);
            userService.update(deleteUser);
            res.put("status",1);
        }
        logger.info("删除用户:{}",userId);
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

    @RequestMapping("/admin/getauthorization.htm")
    public void authorization(HttpServletResponse response,HttpServletRequest request,String user_id,String[] classifys){
        classifys = request.getParameterValues("classifys[]");
        User user = SecurityUserHolder.getCurrentUser();
        User authorizationUser = userService.getObjById(CommUtil.null2Long(user_id));
        Map res = new HashMap();
        res.put("status",0);
        Map map = request.getParameterMap();
        if(user != null && authorizationUser != null){
            List<Classify> classifyList = authorizationUser.getCus();
            int size = classifyList.size();
            for (int i = 0;i<size;i++) {
                Classify c = classifyList.get(0);
                c.getUsers().remove(authorizationUser);
                this.classifyService.update(c);
                authorizationUser.getCus().remove(c);
                this.userService.update(authorizationUser);
            }
            if(classifys != null)
            for (String classifyId:classifys) {
                Classify classify = this.classifyService.getObjById(CommUtil.null2Long(classifyId));
                if(classify != null){
                    classify.getUsers().add(authorizationUser);
                    this.classifyService.update(classify);
                    authorizationUser.getCus().add(classify);
                }
            }
            this.userService.update(authorizationUser);
            List classifyNames = new ArrayList();
            for (Classify c: authorizationUser.getCus()) {
                classifyNames.add(c.getClassifyName());
            }
            res.put("obj",classifyNames);
            res.put("status",1);
        }
        logger.info("给:{}用户授权:{}",user_id,classifys);
        returnView(response,res);
    }

    /**
     * 获取当前已授权的栏目
     * @param response
     * @param request
     * @param user_id
     */
    @RequestMapping("/admin/user_classify.htm")
    public void getUserClassIfy(HttpServletResponse response,HttpServletRequest request,String user_id){
        User currenrUser = SecurityUserHolder.getCurrentUser();
        User user = this.userService.getObjById(CommUtil.null2Long(user_id));
        Map res = new HashMap();
        if(currenrUser != null && user != null){
            res.put("status",1);
            List li = new ArrayList();
            for (Classify c:user.getCus()
                 ) {
                li.add(c.getId());
                System.out.println(c.getClassifyName());
            }
            res.put("classifys",li);
        }else{
            res.put("status",2);
        }
        logger.info("获取当前:{}已授权的栏目",user_id);
        returnView(response,res);
    }


    @RequestMapping("/admin/login_out.htm")
    public void loginOut(HttpServletResponse response,HttpServletRequest request){
        User user = SecurityUserHolder.getCurrentUser();
        if(user != null){
           Cookie[] cookies = request.getCookies();
           for (int i = 0; i<cookies.length; i++){
               logger.info(cookies[i].getName());
               if(cookies[i].getName().equals("school_user_session")){
                   Cookie cookie = cookies[i];
                   cookie.setMaxAge(0);
                   cookie.setValue(null);
                   request.getSession().removeAttribute("user");
                   response.addCookie(cookie);
                   logger.info(":{}退出登录",user.getId());
                   break;
               }
           }
        }
        try {
            response.sendRedirect("/login.htm");
        }catch (IOException e){
            e.printStackTrace();
        }

    }



    @RequestMapping({"/verify.htm"})
    public void verify(HttpServletRequest request, HttpServletResponse response, String name)
            throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        HttpSession session = request.getSession(true);

        /*
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        if (CommUtil.null2String(name).equals(""))
            session.setAttribute("verify_code", verifyCode);
        else{
            session.setAttribute(name, verifyCode);
        }
        int w = 73, h = 27;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
*/

        int width = 73;
        int height = 27;
        BufferedImage image = new BufferedImage(width, height,
                1);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Times New Roman", 0, 24));

        g.setColor(getRandColor(140, 200));
        for (int i = 0; i < 300; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < 4; i++){
            String rand = CommUtil.randomInt(1).toUpperCase();
            sRand = sRand + rand;

            g.setColor(
                    new Color(20 + random.nextInt(110), 20 + random
                            .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 24);
        }

        if (CommUtil.null2String(name).equals(""))
            session.setAttribute("verify_code", sRand);
        else{
            session.setAttribute(name, sRand);
        }

        g.dispose();
        ServletOutputStream responseOutputStream = response.getOutputStream();

        ImageIO.write(image, "JPEG", responseOutputStream);

        responseOutputStream.flush();
        responseOutputStream.close();

    }

    private Color getRandColor(int fc, int bc){
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }


}
