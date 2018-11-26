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
public class UseHistoryEntity extends BaseEntity {

    /**
     * records : [{"glass_number":"2","plan_id":"A1","plan_name":"123","duration_display":"2分0秒","exercise_time_display":"10秒","evaluation":"佩戴不正确","created_at":"2018-06-24"}]
     * per_page : 10
     * pages : 1
     * page : 1
     * total : 10
     */

    private int per_page;
    private int pages;
    private int page;
    private int total;
    private List<RecordsBean> records;

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * glass_number : 2
         * plan_id : A1
         * plan_name : 123
         * duration_display : 2分0秒
         * exercise_time_display : 10秒
         * evaluation : 佩戴不正确
         * created_at : 2018-06-24
         */

        private String glass_number;
        private String plan_id;
        private String plan_name;
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
