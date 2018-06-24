package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/23.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class Register2Bean {
    private String register_code;
    private String vision_type;
    private String left_vision;
    private String left_num;
    private String right_vision;
    private String right_num;
    private String double_vision;
    private String double_num;

    public String getRegister_code() {
        return register_code;
    }

    public void setRegister_code(String register_code) {
        this.register_code = register_code;
    }

    public String getVision_type() {
        return vision_type;
    }

    public void setVision_type(String vision_type) {
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

    @Override
    public String toString() {
        return "Register2Bean{" +
                "register_code='" + register_code + '\'' +
                ", vision_type='" + vision_type + '\'' +
                ", left_vision='" + left_vision + '\'' +
                ", left_num='" + left_num + '\'' +
                ", right_vision='" + right_vision + '\'' +
                ", right_num='" + right_num + '\'' +
                ", double_vision='" + double_vision + '\'' +
                ", double_num='" + double_num + '\'' +
                '}';
    }
}
