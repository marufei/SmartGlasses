package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2019/6/14.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class InfoEntity extends BaseEntity {

    /**
     * user : {"avatar_url":"http://127.0.0.1:8082/static/images/boy.png","name":"测试","tel":"15251815510","birthday":"1990-01-01","idcard":"","sex":1,"sex_display":"男","guardian_name":"测试222","wechat":"zz123456"}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * avatar_url : http://127.0.0.1:8082/static/images/boy.png
         * name : 测试
         * tel : 15251815510
         * birthday : 1990-01-01
         * idcard :
         * sex : 1
         * sex_display : 男
         * guardian_name : 测试222
         * wechat : zz123456
         */

        private String avatar_url;
        private String name;
        private String tel;
        private String birthday;
        private String idcard;
        private int sex;
        private String sex_display;
        private String guardian_name;
        private String wechat;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSex_display() {
            return sex_display;
        }

        public void setSex_display(String sex_display) {
            this.sex_display = sex_display;
        }

        public String getGuardian_name() {
            return guardian_name;
        }

        public void setGuardian_name(String guardian_name) {
            this.guardian_name = guardian_name;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }
    }
}
