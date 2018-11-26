package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.yxys365.smartglasses.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MaRufei
 * on 2018/5/27.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class HomeLvAdapter extends BaseAdapter {
    private Context context;
    private List<BleDevice> list = new ArrayList<>();


    public HomeLvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<BleDevice> list) {
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
            view = View.inflate(context, R.layout.item_home_lv, null);
            viewHolder.item_home_mac = view.findViewById(R.id.item_home_mac);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_home_mac.setText(list.get(i).getName());
        return view;
    }

    class ViewHolder {
        TextView item_home_mac;
    }

}
