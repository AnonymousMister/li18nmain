package com.li18n.model;

import com.li18n.utility.annotation.BeanLi18n;
import com.li18n.utility.annotation.ID;
import com.li18n.utility.annotation.Table;

@Table(name = "sys_lang")
public class Model {


    private String e_lang_type;

    @BeanLi18n(Li18n = "en")
    private String vc_name;

    @ID
    private String vc_code;

    private String e_is_encrypted;

    private String e_type;

    private String e_is_edit;

    private String vc_remark;

    public String getE_lang_type() {
        return e_lang_type;
    }

    public void setE_lang_type(String e_lang_type) {
        this.e_lang_type = e_lang_type;
    }

    public String getVc_name() {
        return vc_name;
    }

    public void setVc_name(String vc_name) {
        this.vc_name = vc_name;
    }

    public String getVc_code() {
        return vc_code;
    }

    public void setVc_code(String vc_code) {
        this.vc_code = vc_code;
    }

    public String getE_is_encrypted() {
        return e_is_encrypted;
    }

    public void setE_is_encrypted(String e_is_encrypted) {
        this.e_is_encrypted = e_is_encrypted;
    }

    public String getE_type() {
        return e_type;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public String getE_is_edit() {
        return e_is_edit;
    }

    public void setE_is_edit(String e_is_edit) {
        this.e_is_edit = e_is_edit;
    }

    public String getVc_remark() {
        return vc_remark;
    }

    public void setVc_remark(String vc_remark) {
        this.vc_remark = vc_remark;
    }
}
