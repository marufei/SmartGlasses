package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/26.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class ExpreciseEntity extends BaseEntity {


    /**
     * wear : {"glass_number":"11","plan_id":"A1","plan_name":"自动方案2","total_exercise_time":"49分20秒","duration_display":"1小时","exercise_time_display":"1分40秒","evaluation":"佩戴不正确","created_at":"2018-06-29"}
     */

    private WearBean wear;

    public WearBean getWear() {
        return wear;
    }

    public void setWear(WearBean wear) {
        this.wear = wear;
    }

    public static class WearBean {
        /**
         * glass_number : 11
         * plan_id : A1
         * plan_name : 自动方案2
         * total_exercise_time : 49分20秒
         * duration_display : 1小时
         * exercise_time_display : 1分40秒
         * evaluation : 佩戴不正确
         * created_at : 2018-06-29
         */

        private String glass_number;
        private String plan_id;
        private String plan_name;
        private String total_exercise_time;
        private String duration_display;
        private String exercise_time_display;
        private String evaluation;
        private String created_at;

        public String getGlass_number() {
            return glass_number;
        }

        public void setGlass_number(String glass_number) {
            this.glass_number = glass_number;
        }

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getTotal_exercise_time() {
            return total_exercise_time;
        }

        public void setTotal_exercise_time(String total_exercise_time) {
            this.total_exercise_time = total_exercise_time;
        }

        public String getDuration_display() {
            return duration_display;
        }

        public void setDuration_display(String duration_display) {
            this.duration_display = duration_display;
        }

        public String getExercise_time_display() {
            return exercise_time_display;
        }

        public void setExercise_time_display(String exercise_time_display) {
            this.exercise_time_display = exercise_time_display;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
