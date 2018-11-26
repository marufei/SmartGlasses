package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/25.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class LoginEntity extends BaseEntity {

    /**
     * data : {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMTIuNzQuMTI3Ljk5OjgwMDJcL2FwaVwvbG9naW4iLCJpYXQiOjE1MzI1MjI4NTUsImV4cCI6MTUzMjk1NDg1NSwibmJmIjoxNTMyNTIyODU1LCJqdGkiOiJ2ZFZuVkRCQ0NWelRNcVJoIiwic3ViIjoyOSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyIsInBhc3N3b3JkX2hhc2giOiIkMnkkMTAkUk14TS5nd3FvelJsSHRXTEtoRVcwLlwvQlZRQm9YTUVqaVJiXC9TVXB6QlYxbjkxbXIyb0ZJUyJ9.uMkBziteRL1jvZtRCcXNlRxfgA88D7R_8I1ixn5P4jk","token_type":"bearer","expires_in":432000,"vision_upload":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMTIuNzQuMTI3Ljk5OjgwMDJcL2FwaVwvbG9naW4iLCJpYXQiOjE1MzI1MjI4NTUsImV4cCI6MTUzMjk1NDg1NSwibmJmIjoxNTMyNTIyODU1LCJqdGkiOiJ2ZFZuVkRCQ0NWelRNcVJoIiwic3ViIjoyOSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyIsInBhc3N3b3JkX2hhc2giOiIkMnkkMTAkUk14TS5nd3FvelJsSHRXTEtoRVcwLlwvQlZRQm9YTUVqaVJiXC9TVXB6QlYxbjkxbXIyb0ZJUyJ9.uMkBziteRL1jvZtRCcXNlRxfgA88D7R_8I1ixn5P4jk
         * token_type : bearer
         * expires_in : 432000
         * vision_upload : 0
         */

        private String access_token;
        private String token_type;
        private int expires_in;
        private int vision_upload;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public int getVision_upload() {
            return vision_upload;
        }

        public void setVision_upload(int vision_upload) {
            this.vision_upload = vision_upload;
        }
    }
}
