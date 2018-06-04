package com.dq.yanglao.bean;

/**
 * Created by jingang on 2018/5/7.
 */

public class Fence {

    /**
     * status : 1
     * data : {"id":"1","device":"3925333600","device_id":"5","point_lat":"35.06348575256905","point_lng":"118.32725867629051","radii":"0"}
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
         * device : 3925333600
         * device_id : 5
         * point_lat : 35.06348575256905
         * point_lng : 118.32725867629051
         * radii : 0
         */

        private String id;
        private String device;
        private String device_id;
        private String point_lat;
        private String point_lng;
        private String radii;

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

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getPoint_lat() {
            return point_lat;
        }

        public void setPoint_lat(String point_lat) {
            this.point_lat = point_lat;
        }

        public String getPoint_lng() {
            return point_lng;
        }

        public void setPoint_lng(String point_lng) {
            this.point_lng = point_lng;
        }

        public String getRadii() {
            return radii;
        }

        public void setRadii(String radii) {
            this.radii = radii;
        }
    }
}
