package com.dq.yanglao.bean;

import java.util.List;

/**
 * Created by jingang on 2018/5/5.
 */

public class DeviceSetting {

    /**
     * status : 1
     * data : [{"act":"SOSSMS","val":"1"},{"act":"LOWBAT","val":"1"},{"act":"REMOVE","val":"0"},{"act":"REMOVESMS","val":"0"},{"act":"PEDO","val":"0"},{"act":"profile","val":"0"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * act : SOSSMS
         * val : 1
         */

        private String act;
        private String val;

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
}
