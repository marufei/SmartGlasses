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
public class BuyTimeEntity extends BaseEntity {

    /**
     * records : [{"days":5,"left_days":4,"status":"进行中","created_at":"2018-06-30"},{"days":5,"left_days":0,"status":"已结束","created_at":"2018-06-30"},{"days":5,"left_days":0,"status":"已结束","created_at":"2018-06-30"}]
     * per_page : 20
     * pages : 1
     * page : 1
     * total : 3
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
         * days : 5
         * left_days : 4
         * status : 进行中
         * created_at : 2018-06-30
         */

        private int days;
        private int left_days;
        private String status;
        private String created_at;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getLeft_days() {
            return left_days;
        }

        public void setLeft_days(int left_days) {
            this.left_days = left_days;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
