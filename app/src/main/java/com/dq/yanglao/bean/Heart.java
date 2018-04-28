package com.dq.yanglao.bean;

/**
 * Created by jingang on 2018/4/28.
 */

public class Heart {

    /**
     * status : 1
     * data : {"id":"11","device_id":"5","bmp":"0","addtime":"2018-04-28 10:10:20"}
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
         * id : 11
         * device_id : 5
         * bmp : 0
         * addtime : 2018-04-28 10:10:20
         */

        private String id;
        private String device_id;
        private String bmp;
        private String addtime;

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

        public String getBmp() {
            return bmp;
        }

        public void setBmp(String bmp) {
            this.bmp = bmp;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
