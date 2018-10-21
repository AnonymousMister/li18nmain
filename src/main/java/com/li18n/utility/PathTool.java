package com.li18n.utility;

public class PathTool {


    @org.jetbrains.annotations.Contract(value = "null, _ -> false; !null, null -> false; !null, !null -> false", pure = true)
    public static boolean pathContain(String path, String name) {
        if (null == path || null == name) {
            return false;
        }
        if (path.indexOf(name) > -1) {
            return true;
        }
        return false;
    }


}
