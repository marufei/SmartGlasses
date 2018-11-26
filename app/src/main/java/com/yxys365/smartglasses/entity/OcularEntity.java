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
public class OcularEntity extends BaseEntity {

    /**
     * records : [{"glass_number":"4","created_at":"2018/02/01"},{"glass_number":"2","created_at":"2018/01/01"}]
     * per_page : 10
     * pages : 1
     * page : 1
     * total : 2
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
         * glass_number : 4
         * created_at : 2018/02/01
         */

        private String glass_number;
        private String created_at;

        public String getGlass_number() {
            return glass_number;
        }

        public void setGlass_number(String glass_number) {
            this.glass_number = glass_number;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
