package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.AutoPlanEntity;

import java.util.List;


/**
 * Created by MaRufei
 * on 2018/5/27.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class DialogLvAdapter extends BaseAdapter {
    private Context context;
    private List<AutoPlanEntity.PlansBean> list;


    public DialogLvAdapter(Context context, List<AutoPlanEntity.PlansBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_dialog_lv, null);
            viewHolder.item_dialog_pic = view.findViewById(R.id.item_dialog_pic);
            viewHolder.item_dialog_title = view.findViewById(R.id.item_dialog_title);
            viewHolder.item_dialog_bac = view.findViewById(R.id.item_dialog_bac);
            viewHolder.item_dialog_time = view.findViewById(R.id.item_dialog_time);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i % 2 == 0) {
            viewHolder.item_dialog_bac.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            viewHolder.item_dialog_bac.setBackgroundColor(context.getResources().getColor(R.color.gray_ed));
        }
        viewHolder.item_dialog_title.setText(list.get(i).getName());
        viewHolder.item_dialog_time.setText(list.get(i).getDuration_display());

        if (list.get(i).isSeclct()) {
            viewHolder.item_dialog_pic.setImageResource(R.mipmap.choose_y);
        } else {
            viewHolder.item_dialog_pic.setImageResource(R.mipmap.choose_n);
        }

        return view;
    }

    class ViewHolder {
        TextView item_dialog_title, item_dialog_time;
        ImageView item_dialog_pic;
        LinearLayout item_dialog_bac;
    }

}
