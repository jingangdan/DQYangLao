package com.dq.yanglao.bean;

import java.util.List;

/**
 * 获取用户所有绑定的设备
 * Created by jingang on 2018/4/25.
 */

public class DeviceGet {

    /**
     * status : 1
     * data : [{"id":"5","device_id":"5","uid":"1","is_primary":"1","apply_status":"1","device":{"id":"5","device":"439225433316007","status":"0","connect_id":"0"}}]
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
         * id : 5
         * device_id : 5
         * uid : 1
         * is_primary : 1
         * apply_status : 1
         * device : {"id":"5","device":"439225433316007","status":"0","connect_id":"0"}
         */

        private String id;
        private String device_id;
        private String uid;
        private String is_primary;
        private String apply_status;
        private String relation;
        private DeviceBean device;
        private boolean isDefault;

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }

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

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
        }

        public static class DeviceBean {
            /**
             * id : 5
             * device : 439225433316007
             * status : 0
             * connect_id : 0
             */

            private String id;
            private String device;
            private String status;
            private String connect_id;


            private String code;
            private String imei;
            private String technology;
            private String elder_id;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public String getTechnology() {
                return technology;
            }

            public void setTechnology(String technology) {
                this.technology = technology;
            }

            public String getElder_id() {
                return elder_id;
            }

            public void setElder_id(String elder_id) {
                this.elder_id = elder_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
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
        }
    }
}
