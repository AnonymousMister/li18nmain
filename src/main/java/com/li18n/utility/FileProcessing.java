package com.li18n.utility;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileProcessing {

    private static final String NEW_LINE;


    static {
        NEW_LINE = System.getProperty("line.separator");
    }


    /**
     * 保存文件
     *
     * @param context
     * @param path
     */
    public void seveFile(String context, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path, false);
            fos.write(context.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file   文档
     * @param source 原始
     * @param target 目标
     * @return
     */
    public String fileText(String file, String source, String target) {
        file = file.replace(source, target);
        return file;
    }

    /**
     *
     * @param file
     * @param source
     * @param target
     * @param comment
     * @return
     */
    public String fileText(String file, String source, String target, String comment) {
        StringBuffer bfile = new StringBuffer(fileText(file, source, target));
        bfile.insert(bfile.substring(bfile.indexOf(target)).indexOf(NEW_LINE)+bfile.indexOf(target),comment);
        return fileInsert(bfile, comment, target).toString();
    }


    public StringBuffer fileInsert(StringBuffer file, String search, String target) {
        int i = file.lastIndexOf(search);
        if (i == -1) {
            return file;
        }
        int c = file.toString().substring(i).indexOf(target);

        if (c == -1) {
            return file;
        }
        int s = file.toString().substring(i + c).indexOf(NEW_LINE);
        if (c == -1) {
            return file;
        }

        file.insert(i + c + s, search);
        return fileInsert(file, search, target);
    }

    public  String replaces(String text,String[] targets,String replacement){

        for (String target:targets){
            text=text.replace(target,replacement);
        }

        return text;
    }

    public  String replaces(String text,String[] targets,String[] replacements){
        if (targets.length!=replacements.length){
            return text;
        }
        for (int i=0;i<targets.length;i++){
            text=text.replace(targets[i],replacements[i]);
        }
        return text;
    }

    /**
     * 获取文件路径
     *
     * @param path
     * @param fileType
     * @return
     */
    public ArrayList<String> filePath(String path, String fileType) {
        ArrayList<String> filePath = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                filePath(files[i].toString(), fileType);
            } else {
                if (!files[i].isDirectory())
                    if (files[i].getName() == null) {
                        continue;
                    }
                String fileName = files[i].getName();
                if (fileName.lastIndexOf(".") == -1) {
                    continue;
                }
                String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                if (fileType.equals(type)) {
                    filePath.add(files[i].getPath());
                }
            }
        }
        return filePath;
    }

    //提取要替换的文本
    public String seekText(String str, String regex) {
        ArrayList<String> texts = seekTextList(str, regex, true);
        return texts.size() > 0 ? texts.get(0) : null;
    }


    //提取要替换的文本
    public ArrayList<String> seekTextList(String str, String regex, boolean repeat) {
        ArrayList<String> ceekText = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (!"".equals(m.group())) {
                ceekText.add(m.group());
            }
        }
        if (repeat) {
            return (ArrayList<String>) ceekText.stream().distinct().collect(Collectors.toList());
        }
        return ceekText;
    }


    //提取要替换的文本
    public ArrayList<Elements> seekTextElements(String str, String key) {
        ArrayList<Elements> ceekText = new ArrayList<>();
        Document parse = Jsoup.parse(str, "UTF-8");
        Elements li18ns = parse.getElementsByAttribute(key);
        for (int i = 0; i < li18ns.size(); i++) {
            ceekText.add(li18ns.eq(i));
        }
        return ceekText;
    }


    public String readFile(File file) {
        StringBuilder text = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                text.append(line + NEW_LINE);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}
