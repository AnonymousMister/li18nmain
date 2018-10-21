package com.li18n.context;

public interface JSContext {

    //是否生成注释
    boolean isjsnoun=false;
    boolean addjsnoun=false;


    String jsLi18nName = "var [A-Za-z1-9_-]+ = function";

    String jsText = "(\"|\')?[$][\\u0391-\\uFFE5A-Za-z1-9_-]+[$](\"|\')?";

    String TranslateText = "[\\u0391-\\uFFE5A-Za-z1-9_-]+";

    String textFunction = "i18n.t('@')";

    String jsnoun = "[//][//][,\\s+\\u0391-\\uFFE5A-Za-z1-9_-]+";

    String li18nTextFile = "/Users/misterzhang/Documents/";
}
