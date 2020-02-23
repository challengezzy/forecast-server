package com.demand.driven.util;

import java.util.*;

/**
 * MD5加解密的工具类
 */
@SuppressWarnings("unused")
public class SignatureUtil {

    public static String sign(Map params, String privateKey) {
        Properties properties = new Properties();

        for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            Object value = params.get(name);

            if (name == null || name.equalsIgnoreCase("sign") || name.equalsIgnoreCase("signType")) {
                continue;
            }
            if (null != value) {
                properties.setProperty(name, value.toString());
            }
        }
        String content = getSignatureContent(properties);
        return sign(content, privateKey);
    }

    /**
     * 获取按字母顺序排序的字符串
     *
     * @param params 需要排序的参数
     * @return 排序后的参数
     */
    public static String getSignature(Map params) {
        Properties properties = new Properties();

        for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            Object value = params.get(name);
            if (name == null || name.equalsIgnoreCase("sign") || name.equalsIgnoreCase("signType")) {
                continue;
            }
            if (null != value) {
                properties.setProperty(name, value.toString());
            }
        }
        String content = getSignatureContent(properties);
        return content;
    }

    public static String getSignatureContent(Properties properties) {
        StringBuffer content = new StringBuffer();
        List keys = new ArrayList(properties.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = properties.getProperty(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        return content.toString();
    }

    /**
     * 正式加密
     * @param content 加密内容
     * @param privateKey 加密密钥
     * @return 加密结果
     */
    public static String sign(String content, String privateKey) {
        if (privateKey == null) {
            return null;
        }
        String signBefore = content + privateKey;
        return Md5EncryptUtil.md5(signBefore);
    }
}
