package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/7/20.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class UpdateVersionEntity extends BaseEntity {

    /**
     * version : {"version_code":"1.0","version_type":1,"url":"","content":""}
     */

    private VersionBean version;

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        /**
         * version_code : 1.0
         * version_type : 1
         * url :
         * content :
         */

        private String version_code;
        private int version_type;
        private String url;
        private String content;

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public int getVersion_type() {
            return version_type;
        }

        public void setVersion_type(int version_type) {
            this.version_type = version_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
