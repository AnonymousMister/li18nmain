package com.li18n.utility.translate;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.li18n.context.BaiDuContext;
import com.li18n.utility.translate.baidu.BaiDuDTO;
import com.li18n.utility.translate.baidu.TransApiBaiDu;

public class TranslateEntrance {
    public BaiDuDTO baiDuTranslateEntrance(String query) {
        TransApiBaiDu api = new TransApiBaiDu();
        return JSON.parseObject(api.getTransResult(query, BaiDuContext.FROM, BaiDuContext.TO), new TypeReference<BaiDuDTO>() {});
    }

}
