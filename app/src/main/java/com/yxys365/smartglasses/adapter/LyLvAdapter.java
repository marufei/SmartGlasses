package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;


/**
 * Created by MaRufei
 * on 2018/5/27.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class LyLvAdapter extends BaseAdapter {
    private Context context;



    public LyLvAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.item_ly,null);
            viewHolder.item_ly_bac=view.findViewById(R.id.item_ly_bac);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        if(i%2==0){
            viewHolder.item_ly_bac.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            viewHolder.item_ly_bac.setBackgroundColor(context.getResources().getColor(R.color.gray_ed));
        }

        return view;
    }
    class ViewHolder{
        TextView item_name,item_address,item_qiangdu;
        Button item_connect,item_disconnect,item_operate;
        LinearLayout item_ly_bac;
    }

}
