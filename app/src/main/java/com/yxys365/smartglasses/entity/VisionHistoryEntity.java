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
public class VisionHistoryEntity extends BaseEntity {

    /**
     * records : [{"left_vision":"1.0","left_num":"+1","right_vision":"1.0","right_num":"+1","double_vision":"1.0","double_num":"+1","created_at":"2018-01-01"}]
     * per_page : 10
     * pages : 1
     * page : 1
     * total : 1
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
         * left_vision : 1.0
         * left_num : +1
         * right_vision : 1.0
         * right_num : +1
         * double_vision : 1.0
         * double_num : +1
         * created_at : 2018-01-01
         */

        private String left_vision;
        private String left_num;
        private String right_vision;
        private String right_num;
        private String double_vision;
        private String double_num;
        private String created_at;

        public String getLeft_vision() {
            return left_vision;
        }

        public void setLeft_vision(String left_vision) {
            this.left_vision = left_vision;
        }

        public String getLeft_num() {
            return left_num;
        }

        public void setLeft_num(String left_num) {
            this.left_num = left_num;
        }

        public String getRight_vision() {
            return right_vision;
        }

        public void setRight_vision(String right_vision) {
            this.right_vision = right_vision;
        }

        public String getRight_num() {
            return right_num;
        }

        public void setRight_num(String right_num) {
            this.right_num = right_num;
        }

        public String getDouble_vision() {
            return double_vision;
        }

        public void setDouble_vision(String double_vision) {
            this.double_vision = double_vision;
        }

        public String getDouble_num() {
            return double_num;
        }

        public void setDouble_num(String double_num) {
            this.double_num = double_num;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
