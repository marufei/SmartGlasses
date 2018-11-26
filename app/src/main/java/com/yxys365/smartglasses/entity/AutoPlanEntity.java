package com.yxys365.smartglasses.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/6/26.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class AutoPlanEntity extends BaseEntity {

    private List<PlansBean> plans;

    public List<PlansBean> getPlans() {
        return plans;
    }

    public void setPlans(List<PlansBean> plans) {
        this.plans = plans;
    }

    public static class PlansBean {
        /**
         * id : A1
         * name : 123
         * duration : 3600
         * duration_display : 1小时
         */

        private String id;
        private String name;
        private int duration;
        private String duration_display;

        private boolean isSeclct;

        public boolean isSeclct() {
            return isSeclct;
        }

        public void setSeclct(boolean seclct) {
            isSeclct = seclct;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getDuration_display() {
            return duration_display;
        }

        public void setDuration_display(String duration_display) {
            this.duration_display = duration_display;
        }
    }
}
