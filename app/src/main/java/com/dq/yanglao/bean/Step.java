package com.dq.yanglao.bean;

/**
 * Created by asus on 2018/5/5.
 */

public class Step {

    /**
     * status : 1
     * data : {"id":"1","s_date":"1525449600","step":"109","device":"3925333600","device_id":"5"}
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
         * s_date : 1525449600
         * step : 109
         * device : 3925333600
         * device_id : 5
         */

        private String id;
        private String s_date;
        private String step;
        private String device;
        private String device_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getS_date() {
            return s_date;
        }

        public void setS_date(String s_date) {
            this.s_date = s_date;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }
    }
}
