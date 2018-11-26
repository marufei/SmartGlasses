package com.yxys365.smartglasses.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/7/8.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class PlanDetailsEntity extends BaseEntity {


    /**
     * plan : {"id":"A","name":"方案A","is_auto":1,"duration":10,"duration_display":"10秒","section_id":"41","section_name":"第一阶段","left_time":5,"left_time_display":"5秒","sections":[{"section_id":"41","name":"第一阶段","duration":10,"duration_display":"10秒","left_time":10,"left_time_display":"10秒"}]}
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
         * id : A
         * name : 方案A
         * is_auto : 1
         * duration : 10
         * duration_display : 10秒
         * section_id : 41
         * section_name : 第一阶段
         * left_time : 5
         * left_time_display : 5秒
         * sections : [{"section_id":"41","name":"第一阶段","duration":10,"duration_display":"10秒","left_time":10,"left_time_display":"10秒"}]
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
        private List<SectionsBean> sections;

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

        public List<SectionsBean> getSections() {
            return sections;
        }

        public void setSections(List<SectionsBean> sections) {
            this.sections = sections;
        }

        public static class SectionsBean {
            /**
             * section_id : 41
             * name : 第一阶段
             * duration : 10
             * duration_display : 10秒
             * left_time : 10
             * left_time_display : 10秒
             */

            private String section_id;
            private String name;
            private int duration;
            private String duration_display;
            private int left_time;
            private String left_time_display;

            public String getSection_id() {
                return section_id;
            }

            public void setSection_id(String section_id) {
                this.section_id = section_id;
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
        }
    }
}
