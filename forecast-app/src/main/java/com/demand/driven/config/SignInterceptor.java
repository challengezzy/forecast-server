package com.demand.driven.config;

import static com.demand.driven.util.ServletUtil.writerToJson;

import com.alibaba.fastjson.JSONObject;
import com.demand.driven.dto.BaseResponse;
import com.demand.driven.util.ServletUtil;
import com.demand.driven.util.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 调用合法性校验
 */
@Slf4j
public class SignInterceptor implements HandlerInterceptor {

   //@Value("${is_dev_env}")
   private String isDevEvn = "true";

    //@Value("${app_config.signKey}")
    private String signKey = "zzyyxxaabbcc";

    //@Value("${app_config.appIdKey}")
    private String appIdKey = "10016";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求路径{},请求IP[{}], {}" ,request.getRequestURI(), request.getRemoteHost(),JSONObject.toJSONString(request.getParameterMap()) );

        if ((!StringUtils.isEmpty(isDevEvn)   && "true".equals(isDevEvn)) ) {
            return true;
        }

        try {
            String requestPath = ServletUtil.getRequestPath(request);
            if (ServletUtil.isIgnorePath(requestPath)) {
                return true;
            }
            log.debug("------{}开始验证签名-------------",requestPath);
            boolean result = doCheckSign(request, response, requestPath);
            log.debug("------{}验证签名结束-------------",requestPath);
            return result;
        } catch (Exception e) {
            log.error("校验签名异常!", e);
            throw e;
        }
    }

    private boolean doCheckSign(HttpServletRequest request, HttpServletResponse response, String requestPath) {
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");

        if (StringUtils.isEmpty(appId) || !appId.equals(appIdKey) ) {
            writerToJson(BaseResponse.error("接入方编号为空或不合法!"), response);
            log.info("接入方编号不能为空!Caller IP:{}", ServletUtil.getIpAddress(request));
            return false;
        }
        if (StringUtils.isEmpty(sign)) {
            writerToJson(BaseResponse.error("签名不能为空!"), response);
            log.info("签名不能为空!Caller IP:{}", ServletUtil.getIpAddress(request));
            return false;
        }

        Map<String, Object> paramsMap = ServletUtil.getParamsMapFromRequest(request);
        log.info("***请求参数Map***=[{}]", JSONObject.toJSONString(paramsMap));
        String mySign = SignatureUtil.sign(paramsMap, signKey);
        if (mySign.equals(sign)) {
            log.debug("接入方:[{}]请求URL:[{}]签名验证通过!", appId, requestPath);
            return true;
        } else {
            writerToJson(BaseResponse.error("签名错误!"), response);
            log.info("接入方:[{}]请求URL:[{}]签名错误！原始签名：[{}], 正确的签名：[{}]", appId, requestPath, sign, mySign);
            return false;
        }
    }

}
