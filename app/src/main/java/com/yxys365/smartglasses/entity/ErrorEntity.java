package com.yxys365.smartglasses.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/9/2.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class ErrorEntity extends BaseEntity {


    /**
     * plan : {"id":"02","name":"方案2","is_auto":0,"duration":300,"duration_display":"5分","section_id":"","section_name":"","left_time":276,"left_time_display":"4分36秒","sections":[]}
     */

    private PlanBean plan;

    public PlanBean getPlan() {
        return plan;
    }

    public void setPlan(PlanBean plan) {
        this.plan = plan;
    }

    public static class PlanBean {
        /**
         * id : 02
         * name : 方案2
         * is_auto : 0
         * duration : 300
         * duration_display : 5分
         * section_id :
         * section_name :
         * left_time : 276
         * left_time_display : 4分36秒
         * sections : []
         */

        private String id;
        private String name;
        private int is_auto;
        private int duration;
        private String duration_display;
        private String section_id;
        private String section_name;
        private int left_time;
        private String left_time_display;
        private List<?> sections;

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

        public int getIs_auto() {
            return is_auto;
        }

        public void setIs_auto(int is_auto) {
            this.is_auto = is_auto;
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

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public int getLeft_time() {
            return left_time;
        }

        public void setLeft_time(int left_time) {
            this.left_time = left_time;
        }

        public String getLeft_time_display() {
            return left_time_display;
        }

        public void setLeft_time_display(String left_time_display) {
            this.left_time_display = left_time_display;
        }

        public List<?> getSections() {
            return sections;
        }

        public void setSections(List<?> sections) {
            this.sections = sections;
        }
    }
}
