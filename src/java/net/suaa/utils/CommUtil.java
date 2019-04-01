package net.suaa.utils;

import net.suaa.core.query.support.IPageList;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class CommUtil {

    public static String null2String(Object s) {
        return s == null ? "" : s.toString();
    }
    public static String getURL(HttpServletRequest request){
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();

        String url = request.getScheme() + "://" + request.getServerName();
        if (null2Int(Integer.valueOf(request.getServerPort())) != 80 && null2Int(Integer.valueOf(request.getServerPort())) != 443)
            url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
        else{
            url = url + contextPath;
        }

        return url;
    }
    public static int null2Int(Object s){
        int v = 0;
        if (s != null)
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException){
            }

        return v;
    }
    public static String generic_domain(HttpServletRequest request){
        String system_domain = "localhost";
        String serverName = request.getServerName();
        if (isIp(serverName))
            system_domain = serverName;
        else{
            system_domain = serverName.substring(serverName.indexOf(".") + 1);
        }

        return system_domain;
    }
    public static boolean isIp(String IP){
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) &&
                    (Integer.parseInt(s[1]) < 255) &&
                    (Integer.parseInt(s[2]) < 255) &&
                    (Integer.parseInt(s[3]) < 255))
                b = true;
        }

        return b;
    }
    public static String trimSpaces(String IP){
        while (IP.startsWith(" ")){
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")){
            IP = IP.substring(0, IP.length() - 1).trim();
        }

        return IP;
    }
    public static final String randomInt(int length){
        if (length < 1){
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters = "0123456789".toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++){
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }

        return new String(randBuffer);
    }
    public static Long null2Long(Object s){
        Long v = Long.valueOf(-1L);
        if (s != null)
            try {
                v = Long.valueOf(Long.parseLong(s.toString()));
            } catch (Exception localException){
            }

        return v;
    }
    public static void saveIPageList2ModelAndView(String url, String staticURL, String params, IPageList pList, ModelAndView mv){
        if (pList != null){
            mv.addObject("objs", pList.getResult());
            mv.addObject("totalPage", new Integer(pList.getPages()));
            mv.addObject("pageSize", Integer.valueOf(pList.getPageSize()));
            mv.addObject("rows", new Integer(pList.getRowCount()));
            mv.addObject("currentPage", new Integer(pList.getCurrentPage()));
            mv.addObject("gotoPageHTML", showPageHtml(url, params, pList.getCurrentPage(), pList.getPages()));
            mv.addObject("gotoPageFormHTML", showPageFormHtml(pList.getCurrentPage(), pList.getPages()));
            mv.addObject("gotoPageStaticHTML", showPageStaticHtml(staticURL, pList.getCurrentPage(), pList.getPages()));
            mv.addObject("gotoPageAjaxHTML", showPageAjaxHtml(url, params, pList.getCurrentPage(), pList.getPages()));
        }
    }
    public static String showPageAjaxHtml(String url, String params, int currentPage, int pages){
        String s = "";
        if (pages > 0){
            String address = url + "?1=1" + params;
            if (currentPage >= 1){
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" +
                        address + "\",1,this)'>首页</a> ";
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" +
                        address +
                        "\"," + (
                        currentPage - 1) +
                        ",this)'>上一页</a> ";
            }

            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages){
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++){
                    if (i == currentPage)
                        s = s + "<a class='this' href='javascript:void(0);' onclick='return ajaxPage(\"" +
                                address +
                                "\"," +
                                i +
                                ",this)'>" +
                                i +
                                "</a> ";
                    else
                        s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" +
                                address + "\"," + i +
                                ",this)'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages){
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" +
                        address +
                        "\"," + (
                        currentPage + 1) +
                        ",this)'>下一页</a> ";
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" +
                        address + "\"," + pages + ",this)'>末页</a> ";
            }
        }

        return s;
    }
    public static String showPageHtml(String url, String params, int currentPage, int pages){
        String s = "";
        if (pages > 0){
            if (currentPage >= 1){
                s = s + "<a href='" + url + "?currentPage=1" + params +
                        "'>首页</a> ";
                if (currentPage > 1){
                    s = s + "<a href='" + url + "?currentPage=" + (
                            currentPage - 1) + params + "'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages){
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++){
                    if (i == currentPage)
                        s = s + "<a class='this' href='" + url + "?currentPage=" +
                                i + params + "'>" + i + "</a> ";
                    else
                        s = s + "<a href='" + url + "?currentPage=" + i + params +
                                "'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages){
                if (currentPage < pages){
                    s = s + "<a href='" + url + "?currentPage=" + (
                            currentPage + 1) + params + "'>下一页</a> ";
                }
                s = s + "<a href='" + url + "?currentPage=" + pages + params +
                        "'>末页</a> ";
            }
        }

        return s;
    }
    public static String showPageFormHtml(int currentPage, int pages){
        String s = "";
        if (pages > 0){
            if (currentPage >= 1){
                s = s + "<a href='javascript:void(0);' onclick='return gotoPage(1)'>首页</a> ";
                if (currentPage > 1){
                    s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + (
                            currentPage - 1) + ")'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages){
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 4); j++){
                    if (i == currentPage)
                        s = s + "<a class='this' href='javascript:void(0);' onclick='return gotoPage(" +
                                i + ")'>" + i + "</a> ";
                    else
                        s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" +
                                i +
                                ")'>" + i + "</a> ";
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages){
                if (currentPage < pages){
                    s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + (
                            currentPage + 1) + ")'>下一页</a> ";
                }
                s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" +
                        pages + ")'>末页</a> ";
            }
        }

        return s;
    }
    public static String showPageStaticHtml(String url, int currentPage, int pages){
        String s = "";
        if (pages > 0){
            if (currentPage >= 1){
                s = s + "<div class='fl pull_page_up'><a href='" + url + "?currentPage=1'>首页</a> </div><ul>";
                if (currentPage > 1){
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage - 1) +
                            "'><li>上一页</li></a>";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages){
              //  s = s + "<li>第　<li>";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++){
                    if (i == currentPage)
                        s = s + "<a class='this' href='" + url + "?currentPage=" + i +
                                "'><li class='on'>" + i + "</li></a> ";
                    else
                        s = s + "<a href='" + url + "?currentPage=" + i + "'><li>" + i +
                                "</li></a> ";
                    i++;
                }

            //    s = s + "<li>页　<li>";
            }
            if (currentPage <= pages){
                if (currentPage < pages){
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage + 1) +
                            "'><li>下一页</li></a> ";
                }
                s = s + "</ul><div class='fl pull_page_up'><a href='" + url + "?currentPage=" + pages + "'>末页</a> </div>";
            }
        }

        return s;
    }
}
