package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.RdActivity;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.entity.QgBean;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;

/**
 * Created by MaRufei
 * on 2018/6/4.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class Rd1Fragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private Button fragment_rd_next;
    /**
     * 屈光检测 符号,球镜，柱镜,最终数据
     */
    private String qg_fh, qg_data1, qg_data2, qg_data;
    /**
     * 柱镜 左右双
     */
    private TextView rd1_zj_left, rd1_zj_right, rd1_zj_both;
    /**
     * 球镜 左右双
     */
    private TextView rd1_qj_left, rd1_qj_right, rd1_qj_both;

    private QgBean qgBean = new QgBean();
    /**
     * 标识 左右双
     */
    private TextView rd1_bs_left, rd1_bs_right, rd1_bs_both;
    /**
     * 最高矫正视力
     */
    private TextView rd1_jz_left, rd1_jz_right, rd1_jz_both;
    /**
     * 轴位
     */
    private TextView rd1_z_left, rd1_z_right, rd1_z_both;
    /**
     * 单选数值
     */
    private String single_data;


    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_rd1, null);
        initView();
        return view;
    }

    private void initView() {
        fragment_rd_next = view.findViewById(R.id.fragment_rd_next);
        fragment_rd_next.setOnClickListener(this);

        rd1_zj_left = view.findViewById(R.id.rd1_zj_left);
        rd1_zj_left.setOnClickListener(this);
        rd1_zj_right = view.findViewById(R.id.rd1_zj_right);
        rd1_zj_right.setOnClickListener(this);
        rd1_zj_both = view.findViewById(R.id.rd1_zj_both);
        rd1_zj_both.setOnClickListener(this);

        rd1_qj_left = view.findViewById(R.id.rd1_qj_left);
        rd1_qj_left.setOnClickListener(this);
        rd1_qj_right = view.findViewById(R.id.rd1_qj_right);
        rd1_qj_right.setOnClickListener(this);
        rd1_qj_both = view.findViewById(R.id.rd1_qj_both);
        rd1_qj_both.setOnClickListener(this);

        rd1_bs_left = view.findViewById(R.id.rd1_bs_left);
        rd1_bs_left.setOnClickListener(this);
        rd1_bs_right = view.findViewById(R.id.rd1_bs_right);
        rd1_bs_right.setOnClickListener(this);
        rd1_bs_both = view.findViewById(R.id.rd1_bs_both);
        rd1_bs_both.setOnClickListener(this);

        rd1_jz_left = view.findViewById(R.id.rd1_jz_left);
        rd1_jz_left.setOnClickListener(this);
        rd1_jz_right = view.findViewById(R.id.rd1_jz_right);
        rd1_jz_right.setOnClickListener(this);
        rd1_jz_both = view.findViewById(R.id.rd1_jz_both);
        rd1_jz_both.setOnClickListener(this);

        rd1_z_left = view.findViewById(R.id.rd1_z_left);
        rd1_z_left.setOnClickListener(this);
        rd1_z_right = view.findViewById(R.id.rd1_z_right);
        rd1_z_right.setOnClickListener(this);
        rd1_z_both = view.findViewById(R.id.rd1_z_both);
        rd1_z_both.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_rd_next:
//                RdActivity.start(getActivity());
//                showDialog1(R.layout.dialog_wheel_one, "目镜");
                break;
            case R.id.rd1_zj_left:
                showDialog2(2, 4);
                break;
            case R.id.rd1_zj_right:
                showDialog2(2, 5);
                break;
            case R.id.rd1_zj_both:
                showDialog2(2, 6);
                break;
            case R.id.rd1_qj_left:
                showDialog2(1, 1);
                break;
            case R.id.rd1_qj_right:
                showDialog2(1, 2);
                break;
            case R.id.rd1_qj_both:
                showDialog2(1, 3);
                break;
            case R.id.rd1_bs_left:
                showDialog1(3, 7);
                break;
            case R.id.rd1_bs_right:
                showDialog1(3, 8);
                break;
            case R.id.rd1_bs_both:
                showDialog1(3, 9);
                break;
            case R.id.rd1_jz_left:
                showDialog1(2, 4);
                break;
            case R.id.rd1_jz_right:
                showDialog1(2, 5);
                break;
            case R.id.rd1_jz_both:
                showDialog1(2, 6);
                break;
            case R.id.rd1_z_left:
                showDialog1(1, 1);
                break;
            case R.id.rd1_z_right:
                showDialog1(1, 2);
                break;
            case R.id.rd1_z_both:
                showDialog1(1, 3);
                break;
        }
    }

    /**
     * 单个选项
     */
    public void showDialog1(int type, final int side) {
        String title = "";
        String[] data = null;
        if (type == 1) {
            title = "轴位";
            data = Const.qg_z;
        } else if (type == 2) {
            title = "最高矫正视力";
            data = Const.s_data;
        } else {
            title = "标识数量";
            data = Const.bs_data;
        }
        DialogBottomView dialogBottomView = new DialogBottomView(getActivity(), R.layout.dialog_wheel_one, title, 1);
        dialogBottomView.setData(data);
        final String[] finalData = data;
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1, String item2, String item3) {
                if (!TextUtils.isEmpty(item2)) {
                    single_data = item2;
                } else {
                    single_data = finalData[0];
                }
                switch (side) {
                    case 1:
                        rd1_z_left.setText(single_data);
                        qgBean.setLeft_ax(single_data);
                        break;
                    case 2:
                        rd1_z_right.setText(single_data);
                        qgBean.setRight_ax(single_data);
                        break;
                    case 3:
                        rd1_z_both.setText(single_data);
                        qgBean.setDouble_ax(single_data);
                        break;
                    case 4:
                        rd1_jz_left.setText(single_data);
                        qgBean.setLeft_vision(single_data);
                        break;
                    case 5:
                        rd1_jz_right.setText(single_data);
                        qgBean.setRight_vision(single_data);
                        break;
                    case 6:
                        rd1_jz_both.setText(single_data);
                        qgBean.setDouble_vision(single_data);
                        break;
                    case 7:
                        rd1_bs_left.setText(single_data);
                        qgBean.setLeft_num(single_data);
                        break;
                    case 8:
                        rd1_bs_right.setText(single_data);
                        qgBean.setRight_num(single_data);
                        break;
                    case 9:
                        rd1_bs_both.setText(single_data);
                        qgBean.setDouble_num(single_data);
                        break;
                }

                MyUtils.Loge(TAG, "qgBean:::" + qgBean.toString());
                RdActivity.test_refraction=qgBean.toString();
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }

    /**
     * 三个选项
     */
    public void showDialog2(int type, final int side) {
        String title = "";
        if (type == 1) {
            title = "球镜";
        } else {
            title = "柱镜";
        }
        DialogBottomView dialogBottomView = new DialogBottomView(getActivity(), R.layout.dialog_wheel_one, title, 2);
        dialogBottomView.setData(Const.qg_zf, Const.qg_qj, Const.qg_zj);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1, String item2, String item3) {
                if (!TextUtils.isEmpty(item1)) {
                    qg_fh = item1;
                } else {
                    qg_fh = Const.qg_zf[0];
                }

                if (!TextUtils.isEmpty(item2)) {
                    qg_data1 = item2;
                } else {
                    qg_data1 = Const.qg_qj[0];
                }

                if (!TextUtils.isEmpty(item3)) {
                    qg_data2 = item3;
                } else {
                    qg_data2 = Const.qg_zj[0];
                }

                qg_data = qg_fh + qg_data1 + "." + qg_data2;
                MyUtils.Loge(TAG, "qg_data:" + qg_data);
                switch (side) {
                    case 1:
                        rd1_qj_left.setText(qg_data);
                        qgBean.setLeft_sph(qg_data);
                        break;
                    case 2:
                        rd1_qj_right.setText(qg_data);
                        qgBean.setRight_sph(qg_data);
                        break;
                    case 3:
                        rd1_qj_both.setText(qg_data);
                        qgBean.setDouble_sph(qg_data);
                        break;
                    case 4:
                        rd1_zj_left.setText(qg_data);
                        qgBean.setLeft_cyl(qg_data);
                        break;
                    case 5:
                        rd1_zj_right.setText(qg_data);
                        qgBean.setRight_cyl(qg_data);
                        break;
                    case 6:
                        rd1_zj_both.setText(qg_data);
                        qgBean.setDouble_cyl(qg_data);
                        break;
                }
                MyUtils.Loge(TAG, "qgBean:" + qgBean.toString());

                RdActivity.test_refraction=qgBean.toString();
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }
}
