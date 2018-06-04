package com.dq.yanglao.bean;

/**
 * Created by asus on 2018/5/7.
 */

public class OldInfo {
    /**
     * status : 1
     * data : {"relation":"兄弟","birth_y":"","birth_m":"","birth_d":"","img":"/Public/Images/elder_head.png","height":"0","nickname":"哈哈","sex":"1","lat":"35.045457","lng":"118.332722"}
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
         * relation : 兄弟
         * birth_y :
         * birth_m :
         * birth_d :
         * img : /Public/Images/elder_head.png
         * height : 0
         * nickname : 哈哈
         * sex : 1
         * lat : 35.045457
         * lng : 118.332722
         */

        private String relation;
        private String birth_y;
        private String birth_m;
        private String birth_d;
        private String img;
        private String height;
        private String nickname;
        private String sex;
        private String lat;
        private String lng;

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getBirth_y() {
            return birth_y;
        }

        public void setBirth_y(String birth_y) {
            this.birth_y = birth_y;
        }

        public String getBirth_m() {
            return birth_m;
        }

        public void setBirth_m(String birth_m) {
            this.birth_m = birth_m;
        }

        public String getBirth_d() {
            return birth_d;
        }

        public void setBirth_d(String birth_d) {
            this.birth_d = birth_d;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }
}
