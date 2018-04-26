package com.dq.yanglao.bean;

/**
 * 绑定设备
 * Created by asus on 2018/4/24.
 */

public class DeviceBind {

    /**
     * status : 1
     * data : {"device_id":"1","uid":"1","is_primary":1,"apply_status":1,"id":"1"}
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
         * device_id : 1
         * uid : 1
         * is_primary : 1
         * apply_status : 1
         * id : 1
         */

        private String device_id;
        private String uid;
        private int is_primary;
        private int apply_status;
        private String id;

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

        public int getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(int is_primary) {
            this.is_primary = is_primary;
        }

        public int getApply_status() {
            return apply_status;
        }

        public void setApply_status(int apply_status) {
            this.apply_status = apply_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
