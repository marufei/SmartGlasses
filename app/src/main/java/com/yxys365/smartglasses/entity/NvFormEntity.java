package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/26.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class NvFormEntity extends BaseEntity {

    /**
     * vision : {"vision_type":1,"left_vision":"1.0","left_num":"+1","right_vision":"1.0","right_num":"+1","double_vision":"1.0","double_num":"+1"}
     */

    private VisionBean vision;

    public VisionBean getVision() {
        return vision;
    }

    public void setVision(VisionBean vision) {
        this.vision = vision;
    }

    public static class VisionBean {
        /**
         * vision_type : 1
         * left_vision : 1.0
         * left_num : +1
         * right_vision : 1.0
         * right_num : +1
         * double_vision : 1.0
         * double_num : +1
         */

        private int vision_type;
        private String left_vision;
        private String left_num;
        private String right_vision;
        private String right_num;
        private String double_vision;
        private String double_num;

        public int getVision_type() {
            return vision_type;
        }

        public void setVision_type(int vision_type) {
            this.vision_type = vision_type;
        }

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
