package com.li18n.jsp;

import com.li18n.context.JSPContext;
import com.li18n.utility.translate.TranslateEntrance;
import com.li18n.utility.translate.baidu.BaiDuDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.List;

public class JspLi18nReplace {

    TranslateEntrance translateEntrance = new TranslateEntrance();
    private Long key;

    private StringBuffer sql;

    public JspLi18nReplace() {
        key = JSPContext.key;
        sql = new StringBuffer();
    }

    public void jspFileByLIst(List<String> paths) {

        if (paths == null) {
            return;
        }
        for (String path : paths) {
            String context = readToString(path);
            jspFileByPath(path, context);
            addFile(sql.toString(), JSPContext.sqlpath + "li18n.sql");
        }


    }


    public void jspFileByPath(String path, String context) {
        File jspFile = new File(path);

        try {

            Document parse = Jsoup.parse(jspFile, "UTF-8");


            Elements li18ns = parse.getElementsByAttribute("li18n");


            if (li18ns == null) {
                return;
            }
            if (li18ns.size() == 0) {
                return;
            }

            for (int i = 0; i < li18ns.size(); i++) {
                System.out.println(key);
                Elements li18n = li18ns.eq(i);
                BaiDuDTO baiDuDTO = translateEntrance.baiDuTranslateEntrance(li18n.text());
                String li18nkey = JSPContext.keyHead + key.toString();
                addsql(baiDuDTO,li18nkey);
                String replace = JSPContext.label.replace("@", li18nkey);
                context = (context.replace(li18n.toString(), replace));
                key = (key+1);
            }
            addFile(context, JSPContext.jspPath + jspFile.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void addsql(BaiDuDTO baiDuDTO, String li18nkey) {
        if (baiDuDTO ==null || baiDuDTO.getTrans_result() == null || baiDuDTO.getTrans_result().get(0)==null){
            return;
        }
        String sql=null;
        sql=(JSPContext.sql.replace(JSPContext.sqlCn,baiDuDTO.getTrans_result().get(0).getSrc()));
        sql=(sql.replace(JSPContext.sqlEn,baiDuDTO.getTrans_result().get(0).getDst()));
        sql=(sql.replace(JSPContext.sqlkey,li18nkey));
        sql=(sql.replace(JSPContext.sqlRemark,baiDuDTO.getTrans_result().get(0).getSrc()));
        this.sql.append(sql);
    }

    public void addFile(String context, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path, false);
            fos.write(context.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }


}
