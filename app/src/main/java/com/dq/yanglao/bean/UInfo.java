package com.dq.yanglao.bean;

/**
 * Created by asus on 2018/5/8.
 */

public class UInfo {

    /**
     * status : 1
     * data : {"id":"1","token":"c0xNo7Vrp8","mobile":"18669974364","name":"刘波","headimg":"/Public/Images/user_head.png"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * token : c0xNo7Vrp8
         * mobile : 18669974364
         * name : 刘波
         * headimg : /Public/Images/user_head.png
         */

        private String id;
        private String token;
        private String mobile;
        private String name;
        private String headimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
