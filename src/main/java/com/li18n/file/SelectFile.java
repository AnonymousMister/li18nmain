package com.li18n.file;

import com.li18n.context.FileContext;

import java.io.File;
import java.util.ArrayList;

public class SelectFile {


    private final ArrayList<String> jspList = new ArrayList<String>();


    private final ArrayList<String> jsList = new ArrayList<String>();


    public SelectFile() {

        filePath(FileContext.filepath);

    }


    public void filePath(String path) {
        //获取路径
        File file = new File(path);
        //获取所有文件或文件夹的对象
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //递归操作，逐一遍历各文件夹内的文件
                filePath(files[i].toString());
            } else {
                if (!files[i].isDirectory())
                    //不是文件夹
                    if (files[i].getName() == null) {
                        continue;
                    }
                String fileName = files[i].getName();
                if (fileName.lastIndexOf(".") == -1) {
                    continue;
                }
                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                if (".jsp".equals(fileType)) {
                    jspList.add(files[i].getPath());
                } else if (".js".equals(fileType)) {
                    jsList.add(files[i].getPath());
                }
            }
        }
    }


    public ArrayList<String> getJspList() {
        return jspList;
    }

    public ArrayList<String> getJsList() {
        return jsList;
    }


}
