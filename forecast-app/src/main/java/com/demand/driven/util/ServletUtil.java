package com.demand.driven.util;

import com.alibaba.fastjson.JSON;
import com.demand.driven.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Slf4j
public final class ServletUtil {

    private ServletUtil() {
    }
    
    public static String getRequestPath(HttpServletRequest request){
        return new UrlPathHelper().getPathWithinApplication(request);
    }

    public static boolean isIgnorePath(String path){
        if (path.matches("/_health_check")) {
            return true;
        }
        if (0 == path.indexOf("/healthCheck/")) {
            return true;
        }

        return false;
    }
    
    @SuppressWarnings("rawtypes") 
	public static void writerToJson(BaseResponse resp, HttpServletResponse response) {
        responseString(JSON.toJSONString(resp), response);
    }
	
    public static void responseString(String data, HttpServletResponse response) {
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(data);
        } catch (IOException e) {
        	log.error("输出数据到客户端异常！data:{}", data, e);
        } finally {
            pw.close();
            //IOUtils.closeQuietly(pw);
        }
    }

    @SuppressWarnings("rawtypes")
	public static Map<String, Object> getParamsMapFromRequest(HttpServletRequest request) {
		Map requestParams = request.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    StringBuilder valueStr = new StringBuilder();
		    for (int i = 0; i < values.length; i++) {
		        if (i == values.length - 1) {
		            valueStr.append(values[i]);
		        } else {
		            valueStr.append(values[i]).append(",");
		        }
		    }
		    params.put(name, valueStr);
		}
		return params;
	}
    
    public static String getIpAddress(HttpServletRequest request) {
        String ips = request.getHeader("x-forwarded-for");
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("Proxy-Client-IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
            ips = request.getRemoteAddr();
        }

        String[] ipArray = ips.split(",");
        String clientIp = null;
        for (String ip : ipArray) {
            if (!("unknown".equalsIgnoreCase(ip))) {
                clientIp = ip;
                break;
            }
        }
        return clientIp;
    }
}
