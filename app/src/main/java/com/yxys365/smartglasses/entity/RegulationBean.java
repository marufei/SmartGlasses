package com.yxys365.smartglasses.entity;

/**
 * Created by MaRufei
 * on 2018/6/24.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class RegulationBean {
    private String positive_regulation;
    private String negative_regulation;
    private String positive_fine_regulation;
    private String negative_fine_regulation;

    @Override
    public String toString() {
        return    "{\"positive_regulation\":\"" + positive_regulation + '\"' +
                ", \"negative_regulation\":\"" + negative_regulation + '\"' +
                ", \"positive_fine_regulation\":\"" + positive_fine_regulation + '\"' +
                ", \"negative_fine_regulation\":\"" + negative_fine_regulation + '\"' +
                '}';
    }

    public String getPositive_regulation() {
        return positive_regulation;
    }

    public void setPositive_regulation(String positive_regulation) {
        this.positive_regulation = positive_regulation;
    }

    public String getNegative_regulation() {
        return negative_regulation;
    }

    public void setNegative_regulation(String negative_regulation) {
        this.negative_regulation = negative_regulation;
    }

    public String getPositive_fine_regulation() {
        return positive_fine_regulation;
    }

    public void setPositive_fine_regulation(String positive_fine_regulation) {
        this.positive_fine_regulation = positive_fine_regulation;
    }

    public String getNegative_fine_regulation() {
        return negative_fine_regulation;
    }

    public void setNegative_fine_regulation(String negative_fine_regulation) {
        this.negative_fine_regulation = negative_fine_regulation;
    }
}
