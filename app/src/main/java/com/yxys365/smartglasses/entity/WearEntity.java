package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/26.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class WearEntity extends BaseEntity {

    /**
     * user : {"avatar_url":"http://112.74.127.99:8002/static/images/default.png","name":"abc","birthday":"2003.01.01","left_days":31,"total_exercise_time":"8小时20分","last_exercise_time":"1小时40分","active_date":"2018-06-13"}
     * vision : {"vision_type":1,"left_vision":"1.0","left_num":"+1","right_vision":"1.0","right_num":"+1","double_vision":"1.0","double_num":"+1"}
     * refraction : {"left_sph":"+1.00","left_cyl":"+1.00","left_ax":"20","left_vision":"0.1","left_num":"+1","right_sph":"+1.00","right_cyl":"+1.00","right_ax":"20","right_vision":"0.1","right_num":"+1","double_sph":"+1.00","double_cyl":"+1.00","double_ax":"20","double_vision":"0.1","double_num":"+1"}
     * current_vision : {"left_vision":"1.0","left_num":"+1","right_vision":"1.0","right_num":"+1","double_vision":"1.0","double_num":"+1","created_at":"2018-01-01"}
     */

    private UserBean user;
    private VisionBean vision;
    private RefractionBean refraction;
    private CurrentVisionBean current_vision;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public VisionBean getVision() {
        return vision;
    }

    public void setVision(VisionBean vision) {
        this.vision = vision;
    }

    public RefractionBean getRefraction() {
        return refraction;
    }

    public void setRefraction(RefractionBean refraction) {
        this.refraction = refraction;
    }

    public CurrentVisionBean getCurrent_vision() {
        return current_vision;
    }

    public void setCurrent_vision(CurrentVisionBean current_vision) {
        this.current_vision = current_vision;
    }

    public static class UserBean {
        /**
         * avatar_url : http://112.74.127.99:8002/static/images/default.png
         * name : abc
         * birthday : 2003.01.01
         * left_days : 31
         * total_exercise_time : 8小时20分
         * last_exercise_time : 1小时40分
         * active_date : 2018-06-13
         */

        private String avatar_url;
        private String name;
        private String birthday;
        private int left_days;
        private String total_exercise_time;
        private String last_exercise_time;
        private String active_date;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getLeft_days() {
            return left_days;
        }

        public void setLeft_days(int left_days) {
            this.left_days = left_days;
        }

        public String getTotal_exercise_time() {
            return total_exercise_time;
        }

        public void setTotal_exercise_time(String total_exercise_time) {
            this.total_exercise_time = total_exercise_time;
        }

        public String getLast_exercise_time() {
            return last_exercise_time;
        }

        public void setLast_exercise_time(String last_exercise_time) {
            this.last_exercise_time = last_exercise_time;
        }

        public String getActive_date() {
            return active_date;
        }

        public void setActive_date(String active_date) {
            this.active_date = active_date;
        }
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

    public static class RefractionBean {
        /**
         * left_sph : +1.00
         * left_cyl : +1.00
         * left_ax : 20
         * left_vision : 0.1
         * left_num : +1
         * right_sph : +1.00
         * right_cyl : +1.00
         * right_ax : 20
         * right_vision : 0.1
         * right_num : +1
         * double_sph : +1.00
         * double_cyl : +1.00
         * double_ax : 20
         * double_vision : 0.1
         * double_num : +1
         */

        private String left_sph;
        private String left_cyl;
        private String left_ax;
        private String left_vision;
        private String left_num;
        private String right_sph;
        private String right_cyl;
        private String right_ax;
        private String right_vision;
        private String right_num;
        private String double_sph;
        private String double_cyl;
        private String double_ax;
        private String double_vision;
        private String double_num;

        public String getLeft_sph() {
            return left_sph;
        }

        public void setLeft_sph(String left_sph) {
            this.left_sph = left_sph;
        }

        public String getLeft_cyl() {
            return left_cyl;
        }

        public void setLeft_cyl(String left_cyl) {
            this.left_cyl = left_cyl;
        }

        public String getLeft_ax() {
            return left_ax;
        }

        public void setLeft_ax(String left_ax) {
            this.left_ax = left_ax;
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

        public String getRight_sph() {
            return right_sph;
        }

        public void setRight_sph(String right_sph) {
            this.right_sph = right_sph;
        }

        public String getRight_cyl() {
            return right_cyl;
        }

        public void setRight_cyl(String right_cyl) {
            this.right_cyl = right_cyl;
        }

        public String getRight_ax() {
            return right_ax;
        }

        public void setRight_ax(String right_ax) {
            this.right_ax = right_ax;
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

        public String getDouble_sph() {
            return double_sph;
        }

        public void setDouble_sph(String double_sph) {
            this.double_sph = double_sph;
        }

        public String getDouble_cyl() {
            return double_cyl;
        }

        public void setDouble_cyl(String double_cyl) {
            this.double_cyl = double_cyl;
        }

        public String getDouble_ax() {
            return double_ax;
        }

        public void setDouble_ax(String double_ax) {
            this.double_ax = double_ax;
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

    public static class CurrentVisionBean {
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
