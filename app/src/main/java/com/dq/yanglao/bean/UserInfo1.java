package com.dq.yanglao.bean;

/**
 * 登录返回（成功）
 * Created by jingang on 2018/4/24.
 */

public class UserInfo1 {
    /**
     * status : 1
     * data : {"id":"5","mobile":"17865069351","name":"","status":"0","connect_id":"0","token":"XYwCJeTR6f","headimg":""}
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
         * id : 5
         * mobile : 17865069351
         * name :
         * status : 0
         * connect_id : 0
         * token : XYwCJeTR6f
         * headimg :
         */

        private String id;
        private String mobile;
        private String name;
        private String status;
        private String connect_id;
        private String token;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getConnect_id() {
            return connect_id;
        }

        public void setConnect_id(String connect_id) {
            this.connect_id = connect_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }


//    /**
//     * status : 1
//     * data : {"id":"1","mobile":"18669974364","name":"刘波","status":"0","connect_id":"0","token":"7VPSyKgEdP"}
//     */
//
//    private int status;
//    private DataBean data;
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 1
//         * mobile : 18669974364
//         * name : 刘波
//         * status : 0
//         * connect_id : 0
//         * token : 7VPSyKgEdP
//         */
//
//        private String id;
//        private String mobile;
//        private String name;
//        private String status;
//        private String connect_id;
//        private String token;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getConnect_id() {
//            return connect_id;
//        }
//
//        public void setConnect_id(String connect_id) {
//            this.connect_id = connect_id;
//        }
//
//        public String getToken() {
//            return token;
//        }
//
//        public void setToken(String token) {
//            this.token = token;
//        }
//    }
}
