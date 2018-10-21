package com.li18n.utility.translate.baidu;

import java.util.HashMap;
import java.util.Map;

public class TransApiBaiDu {


    private final String appid="20181008000216226";
    private final String securityKey= "cAqswbJtktnvaL7YY3Io";
    private final String TRANS_API_HOST="http://api.fanyi.baidu.com/api/trans/vip/translate";
    public TransApiBaiDu() {

    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
