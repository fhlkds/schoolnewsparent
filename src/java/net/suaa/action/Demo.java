package net.suaa.action;

import com.alibaba.fastjson.JSONObject;
import net.suaa.domain.Student;
import net.suaa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class Demo {

    @Autowired
    private IStudentService iStudentService;

    @RequestMapping("/demo.htm")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response){

        System.out.println(request.getContextPath());
        System.out.println("demo");
        Student student = new Student();
        student.setStuName("刘备");
        iStudentService.save(student);
        System.out.println("demo2");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login.html");
        return mv;
    }
    @RequestMapping("/mi.htm")
    public void asss(HttpServletRequest request,HttpServletResponse response){
        System.out.println("demo");
    }

    @RequestMapping("location.htm")
    public void locationPosition(HttpServletRequest request,HttpServletResponse response){
        URL u = null;
        String url = "https://restapi.amap.com/v3/ip?key=0f111af513c49c69900bf918a004e4de&ip=114.247.50.2";
        try {
            u = new URL(url);
            Object s =  u.getContent();
            String json = JSONObject.toJSONString(s);
            System.out.println(json);
            System.out.println(s);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch ( IOException e1){
            e1.printStackTrace();
        }
    }
    @RequestMapping("aa.htm")
    public void testAdmin(){
       Student student = iStudentService.getObjectById(1L);
        System.out.println(student.toString());
    }
}
