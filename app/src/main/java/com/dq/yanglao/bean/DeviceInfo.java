package com.dq.yanglao.bean;

/**
 * 获取用户绑定设备或者申请授权
 * Created by jingang on 2018/4/25.
 */

public class DeviceInfo {


    /**
     * status : 1
     * data : {"id":"84","device_id":"5","uid":"2","is_primary":"0","apply_status":"0","relation":"","device_name":"","uinfo":{"id":"2","mobile":"17865069350","name":"赵金刚","headimg":""}}
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
         * id : 84
         * device_id : 5
         * uid : 2
         * is_primary : 0
         * apply_status : 0
         * relation :
         * device_name :
         * uinfo : {"id":"2","mobile":"17865069350","name":"赵金刚","headimg":""}
         */

        private String id;
        private String device_id;
        private String uid;
        private String is_primary;
        private String apply_status;
        private String relation;
        private String device_name;
        private UinfoBean uinfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(String is_primary) {
            this.is_primary = is_primary;
        }

        public String getApply_status() {
            return apply_status;
        }

        public void setApply_status(String apply_status) {
            this.apply_status = apply_status;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public UinfoBean getUinfo() {
            return uinfo;
        }

        public void setUinfo(UinfoBean uinfo) {
            this.uinfo = uinfo;
        }

        public static class UinfoBean {
            /**
             * id : 2
             * mobile : 17865069350
             * name : 赵金刚
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
}
