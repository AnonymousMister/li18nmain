package com.li18n.js;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.li18n.context.JSContext;
import com.li18n.utility.FileProcessing;
import com.li18n.utility.PathTool;
import com.li18n.utility.translate.TranslateEntrance;
import com.li18n.utility.translate.baidu.BaiDuDTO;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JsLi18nReplace {

    TranslateEntrance translateEntrance = new TranslateEntrance();

    private Map<String, List<Map<String, String>>> li18nTextEn = new HashMap<>();
    private Map<String, List<Map<String, String>>> li18nTextCn = new HashMap<>();
    private String keyName;
    private String parentName;
    private FileProcessing fileProcessing = new FileProcessing();


    public void jsFileByLIst(List<String> paths) {

        if (paths == null) {
            return;
        }
        for (String path : paths) {
            File jsFile = new File(path);
            if (null != parentName && !PathTool.pathContain(path, jsFile.getParentFile().getName())) {
                sevenJson(li18nTextCn,"-zh_CN.json");
                sevenJson(li18nTextEn,"-en_US.json");
            }
            parentName = jsFile.getParentFile().getName();
            verbText(jsFile);
        }

        sevenJson(li18nTextCn,"-zh_CN.json");
        sevenJson(li18nTextEn,"-en_US.json");

    }


    private void sevenJson(Object object,String type) {
        keyName = null;
        String o = JSONObject.toJSONString(object);
        o=fileProcessing.replaces(o,new String[]{"{","}"},"");
        o=fileProcessing.replaces(o,new String[]{"[","]"},new String[]{"{","},"});

        fileProcessing.seveFile("{"+o+"}", parentName + type);
    }

    private void verbText(File jsFile) {
        String file = fileProcessing.readFile(jsFile);
        if (keyName == null) {
            String text = fileProcessing.seekText(file, JSContext.jsLi18nName);
            text = text.replace("var", "");
            keyName = text.substring(0, text.indexOf("=")).replace(" ", "").toUpperCase();
            li18nTextEn = new HashMap<>();
            li18nTextCn = new HashMap<>();
            li18nTextEn.put(keyName, new ArrayList<Map<String, String>>());
            li18nTextCn.put(keyName, new ArrayList<Map<String, String>>());
        }
        ArrayList<String> texts = fileProcessing.seekTextList(file, JSContext.jsText, true);
        for (String text : texts) {
            BaiDuDTO baiDuDTO = translateEntrance.baiDuTranslateEntrance(fileProcessing.seekText(text, JSContext.TranslateText));
            String li18nKey = baiDuDTO.getTransDst(0).toUpperCase().trim().replace(" ", "_");
            file = fileProcessing.fileText(file, text, JSContext.textFunction.replace("@", keyName + "." + li18nKey), "/**     * " + baiDuDTO.getTransSrc(0) + "   */");
            List<Map<String, String>> maps = li18nTextEn.get(keyName);
            maps.add(ImmutableMap.of(li18nKey, baiDuDTO.getTransDst(0)));
            li18nTextEn.put(keyName, maps);
            maps = li18nTextCn.get(keyName);
            maps.add(ImmutableMap.of(li18nKey, baiDuDTO.getTransSrc(0)));
            li18nTextCn.put(keyName, maps);
        }
        fileProcessing.seveFile(file, jsFile.getName());
    }


}

