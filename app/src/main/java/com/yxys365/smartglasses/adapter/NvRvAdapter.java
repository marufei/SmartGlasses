package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.NvFormEntity;
import com.yxys365.smartglasses.entity.VisionHistoryEntity;
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
public class NvRvAdapter extends RecyclerView.Adapter<NvRvAdapter.MyViewHolder> {
    private Context mContext;
    private List<VisionHistoryEntity.RecordsBean> list=new ArrayList<>();
    private LayoutInflater inflater;

    public void setList(List<VisionHistoryEntity.RecordsBean> list) {
        this.list = list;
    }

    public NvRvAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nv_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item_nv_left.setText(list.get(position).getLeft_vision());
        holder.item_nv_right.setText(list.get(position).getRight_vision());
        holder.item_nv_double.setText(list.get(position).getDouble_vision());
        holder.item_nv_time.setText(list.get(position).getCreated_at());
        holder.item_nv_double_num.setText(list.get(position).getDouble_num());
        holder.item_nv_left_num.setText(list.get(position).getLeft_num());
        holder.item_nv_right_num.setText(list.get(position).getRight_num());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView item_nv_time, item_nv_left, item_nv_right, item_nv_double;
        TextView item_nv_left_num,item_nv_right_num,item_nv_double_num;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_nv_time = itemView.findViewById(R.id.item_nv_time);
            item_nv_left = itemView.findViewById(R.id.item_nv_left);
            item_nv_right = itemView.findViewById(R.id.item_nv_right);
            item_nv_double = itemView.findViewById(R.id.item_nv_double);
            item_nv_left_num=itemView.findViewById(R.id.item_nv_left_num);
            item_nv_right_num=itemView.findViewById(R.id.item_nv_right_num);
            item_nv_double_num=itemView.findViewById(R.id.item_nv_double_num);

        }
    }

}
