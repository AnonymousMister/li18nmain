package com.li18n.context;

public interface JSPContext {


    //拼接sql
    String sql = "INSERT INTO sys_lang(e_lang_type, vc_name, vc_code, e_is_encrypted, e_type, e_is_edit, vc_remark) VALUES ('zh_CN', '${CN}', '${li18nKey}', '0', 'i18n_page', '0', '${Remark}');" +
            "INSERT INTO sys_lang(e_lang_type, vc_name, vc_code, e_is_encrypted, e_type, e_is_edit, vc_remark) VALUES ('en_US', '${US}', '${li18nKey}', '0', 'i18n_page', '0', '${Remark}');";



    //中文替换符号
    String sqlCn="${CN}";

    //英文替换符号
    String sqlEn="${US}";

    //li18nKey替换符
    String sqlkey="${li18nKey}";

    //sql备注
    String sqlRemark="${Remark}";

    //sql输出路径
    String sqlpath="/Users/misterzhang/Documents/sql/";

    //key值后缀
    Long key = 100012100000L;

    //国际化key前缀
    String keyHead = "I18N-PAGE-";

    //替换标签
    String label = "<a text='@'/>";

    //输出路径
    String jspPath = "/Users/misterzhang/Documents/";


}
