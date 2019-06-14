package com.yxys365.smartglasses.entity;

import java.io.Serializable;

/**
 * Created by MaRufei
 * on 2018/6/21.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class Register1Bean implements Serializable{
    private String tel;
    private String password;
    private String password_confirmation;
    private String vercode;
    private String name;
    /**
     * 性别(1:男,2:女)
     */
    private String sex;
    private String birthday;
    /**
     * 证件类型(1:身份证,2:护照)
     */
    private String doc_type;
    /**
     * 证件号码
     */
    private String doc_number;
    private String wechat;
    private String email;
    private String guardian_name;
    private String guardian_tel;
    private String idcard;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getDoc_number() {
        return doc_number;
    }

    public void setDoc_number(String doc_number) {
        this.doc_number = doc_number;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuardian_name() {
        return guardian_name;
    }

    public void setGuardian_name(String guardian_name) {
        this.guardian_name = guardian_name;
    }

    public String getGuardian_tel() {
        return guardian_tel;
    }

    public void setGuardian_tel(String guardian_tel) {
        this.guardian_tel = guardian_tel;
    }

    @Override
    public String toString() {
        return "Register1Bean{" +
                "tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", password_confirmation='" + password_confirmation + '\'' +
                ", vercode='" + vercode + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", doc_type='" + doc_type + '\'' +
                ", doc_number='" + doc_number + '\'' +
                ", wechat='" + wechat + '\'' +
                ", email='" + email + '\'' +
                ", guardian_name='" + guardian_name + '\'' +
                ", guardian_tel='" + guardian_tel + '\'' +
                '}';
    }
}
