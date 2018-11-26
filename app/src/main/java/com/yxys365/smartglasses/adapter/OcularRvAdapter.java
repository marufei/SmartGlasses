package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.BuyTimeEntity;
import com.yxys365.smartglasses.entity.OcularEntity;
import com.yxys365.smartglasses.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaRufei
 * on 2018/6/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class OcularRvAdapter extends RecyclerView.Adapter<OcularRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<OcularEntity.RecordsBean> list = new ArrayList<>();
    private LayoutInflater inflater;

    public void setList(List<OcularEntity.RecordsBean> list) {
        this.list = list;
    }

    public OcularRvAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_ocular, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.item_ocular_date.setText(list.get(position).getCreated_at());
        holder.item_ocular_number.setText(list.get(position).getGlass_number());
        if (position % 2 == 0) {
            holder.item_ocular_bac.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.item_ocular_bac.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ed));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView item_ocular_date, item_ocular_number;
        LinearLayout item_ocular_bac;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_ocular_date = itemView.findViewById(R.id.item_ocular_date);
            item_ocular_number = itemView.findViewById(R.id.item_ocular_number);
            item_ocular_bac = itemView.findViewById(R.id.item_ocular_bac);
        }
    }

}
