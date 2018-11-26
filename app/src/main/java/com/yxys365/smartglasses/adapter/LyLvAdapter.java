package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.LyVisionEntity;

import java.util.List;


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
    private List<LyVisionEntity.RecordsBean> list;

    public LyLvAdapter(Context context,List<LyVisionEntity.RecordsBean> list){
        this.context=context;
        this.list=list;
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
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.item_ly,null);
            viewHolder.item_ly_bac=view.findViewById(R.id.item_ly_bac);
            viewHolder.item_ly_time=view.findViewById(R.id.item_ly_time);
            viewHolder.item_ly_left=view.findViewById(R.id.item_ly_left);
            viewHolder.item_ly_right=view.findViewById(R.id.item_ly_right);
            viewHolder.item_ly_double=view.findViewById(R.id.item_ly_double);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        if(i%2==0){
            viewHolder.item_ly_bac.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            viewHolder.item_ly_bac.setBackgroundColor(context.getResources().getColor(R.color.gray_ed));
        }
        viewHolder.item_ly_time.setText(list.get(i).getDate());
        if(list.get(i).getVision()!=null&&!TextUtils.isEmpty(list.get(i).getVision().getLeft_vision())) {
            viewHolder.item_ly_left.setText(list.get(i).getVision().getLeft_vision());
        }else {
            viewHolder.item_ly_left.setText("--");
        }
        if(list.get(i).getVision()!=null&&!TextUtils.isEmpty(list.get(i).getVision().getRight_vision())) {
            viewHolder.item_ly_right.setText(list.get(i).getVision().getRight_vision());
        }else {
            viewHolder.item_ly_right.setText("--");
        }
        if(list.get(i).getVision()!=null&&!TextUtils.isEmpty(list.get(i).getVision().getDouble_vision())) {
            viewHolder.item_ly_double.setText(list.get(i).getVision().getDouble_vision());
        }else {
            viewHolder.item_ly_double.setText("--");
        }

        return view;
    }
    class ViewHolder{
        TextView item_ly_time,item_ly_right,item_ly_left,item_ly_double;
        LinearLayout item_ly_bac;
    }

}
