package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/8/7.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class RemindEntity extends BaseEntity {

    /**
     * glass_remind : 1
     * glass_days : 27
     * device_time_remind : 0
     * left_days : 100
     */

    private int glass_remind;
    private int glass_days;
    private int device_time_remind;
    private int left_days;

    public int getGlass_remind() {
        return glass_remind;
    }

    public void setGlass_remind(int glass_remind) {
        this.glass_remind = glass_remind;
    }

    public int getGlass_days() {
        return glass_days;
    }

    public void setGlass_days(int glass_days) {
        this.glass_days = glass_days;
    }

    public int getDevice_time_remind() {
        return device_time_remind;
    }

    public void setDevice_time_remind(int device_time_remind) {
        this.device_time_remind = device_time_remind;
    }

    public int getLeft_days() {
        return left_days;
    }

    public void setLeft_days(int left_days) {
        this.left_days = left_days;
    }
}
