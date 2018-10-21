package com.li18n.utility.translate.baidu;

import java.util.List;

public class BaiDuDTO {


    private String from;

    private String to;

    private List<Trans> trans_result;


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<Trans> getTrans_result() {
        return trans_result;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTrans_result(List<Trans> trans_result) {
        this.trans_result = trans_result;
    }

    public String getTransSrc(int i){
        if ( this.trans_result == null|| this.trans_result.size()==0){
            return null;
        }
        return this.trans_result.get(i).getSrc();
    }
    public String getTransDst(int i){
        if ( this.trans_result == null|| this.trans_result.size()==0){
            return null;
        }
        return this.trans_result.get(i).getDst();
    }

}

