package org.yiming.networktools.request;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析请求
 */
public class AnalysisRequest {

    private HttpServletRequest request;

    public AnalysisRequest(HttpServletRequest request){
        this.request = request;
    }

    /**
     * 获取ip
//     * @param request 请求
     * @return IP
     */
    public String getIp(){
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取系统
//     * @param request 请求
     * @return 系统
     */
    public String getOs(){
        String  userAgent = request.getHeader("User-Agent");
        String os;
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if(userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if(userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if(userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if(userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        }else{
            os = "UnKnown, More-Info: "+userAgent;
        }
        return os;
    }

    /**
     * 获取浏览器
//     * @param request 请求
     * @return 浏览器
     */
    public String getBrowser(){
        String  userAgent       =   request.getHeader("User-Agent");
        String  user            =   userAgent.toLowerCase();
        String browser = null;
        if (user.contains("edge")) {
            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie")) {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera")) {
            if(user.contains("opera")){
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        +"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            }else if(user.contains("opr")){
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (user.contains("chrome")) {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  || (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) || (user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) ) {
            browser = "Netscape-?";
        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
        } else {
             browser = "UnKnown, More-Info: "+userAgent;
        }
        return browser;
    }
}
