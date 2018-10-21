package com.li18n.start;


import com.li18n.file.SelectFile;
import com.li18n.js.JsLi18nReplace;
import com.li18n.jsp.JspLi18nReplace;


public class Main {

    public static void main(String[] args) {
        SelectFile file = new SelectFile();
        JsLi18nReplace js = new JsLi18nReplace();
        js.jsFileByLIst(file.getJsList());
        JspLi18nReplace jsp = new JspLi18nReplace();
        jsp.jspFileByLIst(file.getJspList());


    }
}
