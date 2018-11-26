package com.yxys365.smartglasses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.UseHistoryEntity;
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
public class UseHistoryRvAdapter extends RecyclerView.Adapter<UseHistoryRvAdapter.MyViewHolder> {
    private Context mContext;
    private List<UseHistoryEntity.RecordsBean> list=new ArrayList<>();
    private LayoutInflater inflater;

    public void setList(List<UseHistoryEntity.RecordsBean> list) {
        this.list = list;
    }

    public UseHistoryRvAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_use_device, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.item_use_history_reduce.setText(list);
        holder.item_use_history_reduce.setText(list.get(position).getExercise_time_display());
        holder.item_use_history_all.setText(list.get(position).getDuration_display());
        holder.item_use_history_time.setText(list.get(position).getCreated_at());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView item_use_history_type, item_use_history_reduce, item_use_history_all, item_use_history_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_use_history_type = itemView.findViewById(R.id.item_use_history_type);
            item_use_history_reduce = itemView.findViewById(R.id.item_use_history_reduce);
            item_use_history_all = itemView.findViewById(R.id.item_use_history_all);
            item_use_history_time = itemView.findViewById(R.id.item_use_history_time);
        }
    }

}
