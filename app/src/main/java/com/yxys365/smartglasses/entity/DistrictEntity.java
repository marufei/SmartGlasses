package com.yxys365.smartglasses.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/6/25.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class DistrictEntity extends BaseEntity{


    private List<RegionsBean> regions;

    public List<RegionsBean> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionsBean> regions) {
        this.regions = regions;
    }

    public static class RegionsBean {
        /**
         * adcode : 110000
         * padcode : 100000
         * name : 北京市
         * level : province
         */

        private String adcode;
        private String padcode;
        private String name;
        private String level;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getPadcode() {
            return padcode;
        }

        public void setPadcode(String padcode) {
            this.padcode = padcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
