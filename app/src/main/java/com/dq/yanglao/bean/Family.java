package com.dq.yanglao.bean;

import java.util.List;

/**
 * Created by jingang on 2018/5/8.
 */

public class Family {

    /**
     * status : 1
     * data : [{"id":"1","mobile":"18669974364","name":"刘波","headimg":""},{"id":"3","mobile":"17865069035","name":"","headimg":""}]
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
         * id : 1
         * mobile : 18669974364
         * name : 刘波
         * headimg :
         */

        private String id;
        private String mobile;
        private String name;
        private String headimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
