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
public class LyVisionEntity extends BaseEntity {


    private List<RecordsBean> records;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * date : 2018-06-24
         * simple_date : 06.24
         * vision : {"left_vision":"1.0","left_num":"+1","right_vision":"1.0","right_num":"+1","double_vision":"1.0","double_num":"+1"}
         */

        private String date;
        private String simple_date;
        private VisionBean vision;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSimple_date() {
            return simple_date;
        }

        public void setSimple_date(String simple_date) {
            this.simple_date = simple_date;
        }

        public VisionBean getVision() {
            return vision;
        }

        public void setVision(VisionBean vision) {
            this.vision = vision;
        }

        public static class VisionBean {
            /**
             * left_vision : 1.0
             * left_num : +1
             * right_vision : 1.0
             * right_num : +1
             * double_vision : 1.0
             * double_num : +1
             */

            private String left_vision;
            private String left_num;
            private String right_vision;
            private String right_num;
            private String double_vision;
            private String double_num;

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
        }
    }
}
